package api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import org.testng.annotations.Test;
import java.io.InputStream;
import java.util.Properties;

@Epic("Testes de API")
@Feature("Obter Lista de Produtos")
public class GetProductsTest {

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
    @Description("Verifica se é possível obter a lista de produtos.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testObterListaDeProdutosSucesso() {

        RestAssured.baseURI = baseUrl;
        given()
                .when()
                .get("/products")
                .then()
                .assertThat()
                .statusCode(200)
                .body("products.id", hasItem(1)) // Verifica o ID do primeiro produto na lista
                .body("products.title", hasItem("iPhone 9")) // Verifica o título do primeiro produto na lista
                .body("products.price", hasItem(549)) // Verifica o preço do primeiro produto na lista
                .body("products.stock", hasItem(94)) // Verifica o estoque do primeiro produto na lista
                .body("products.rating", hasItem(4.69f)) // Verifica a avaliação do primeiro produto na lista
                .body("products.thumbnail", hasItem("https://cdn.dummyjson.com/product-images/1/thumbnail.jpg")); // Verifica o thumbnail do primeiro produto na lista

    }
}
