package api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Properties;

@Epic("Testes de API")
@Feature("Obter Produtos por ID")
public class GetProductsIDTest {
    public static Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream input = GetApiTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    private String baseUrl = loadProperties().getProperty("BASE_URL");

    @Test
    @Description("Verifica se é possível obter produtos pelo ID.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testObterProdutosPeloIDSucesso() {

        int productId = 1;

        RestAssured.baseURI = baseUrl;

        given()
                .pathParam("productId", productId)
                .when()
                .get("/products/{productId}")
                .then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.JSON)
                .assertThat().body("id", equalTo(productId))
                .assertThat().body("title", equalTo("iPhone 9"))
                .assertThat().body("price", equalTo(549))
                .assertThat().body("stock", equalTo(94))
                .assertThat().body("rating", equalTo(4.69f))
                .assertThat().body("thumbnail", equalTo("https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"));

    }

    @Test
    @Description("Verifica se é possível obter produtos por ID Inválido.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testObeterProdutosPorIDInvalido() {

        int invalidProductId = 6040;

        RestAssured.baseURI = baseUrl;

        given()
                .pathParam("productId", invalidProductId)
                .when()
                .get("/products/{productId}")
                .then()
                .assertThat().statusCode(404)
                .assertThat().body("message", equalTo("Product with id '6040' not found"));
    }
}
