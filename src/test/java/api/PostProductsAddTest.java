package api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;
import java.io.InputStream;
import java.util.Properties;

@Epic("Testes de API")
@Feature("Adicionar Produto")
public class PostProductsAddTest {

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
    @Description("Verifica se é possível adicionar um novo produto com dados válidos.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testAdicionarProdutoComSucesso() {
        RestAssured.baseURI = baseUrl;
        given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"title\": \"Bermuda Floral\",\n" +
                        "    \"description\": \"Mega Discount, Impression of A...\",\n" +
                        "    \"price\": 13,\n" +
                        "    \"discountPercentage\": 8.4,\n" +
                        "    \"rating\": 4.26,\n" +
                        "    \"stock\": 65,\n" +
                        "    \"brand\": \"Bermuda para praia\",\n" +
                        "    \"category\": \"fragrances\",\n" +
                        "    \"thumbnail\": \"https://i.dummyjson.com/data/products/11/thumnail.jpg\"\n" +
                        "}")
                .when()
                .post("/products/add")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("id", equalTo(101))
                .assertThat().body("title", equalTo("Bermuda Floral"))
                .assertThat().body("price", equalTo(13))
                .assertThat().body("stock", equalTo(65))
                .assertThat().body("rating", equalTo(4.26f))
                .assertThat().body("thumbnail", equalTo("https://i.dummyjson.com/data/products/11/thumnail.jpg"))
                .assertThat().body("description", equalTo("Mega Discount, Impression of A..."))
                .assertThat().body("brand", equalTo("Bermuda para praia"))
                .assertThat().body("category", equalTo("fragrances"));


    }

    @Test
    @Description("Verifica o comportamento quando os dados do produto são inválidos.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testAdicionarProdutoComDadosFaltantes() {
        RestAssured.baseURI = baseUrl;
        given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"title\": \"\",\n" +
                        "    \"description\": \"\",\n" +
                        "    \"price\": 13,\n" +
                        "    \"discountPercentage\": 8.4,\n" +
                        "    \"rating\": 4.26,\n" +
                        "    \"stock\": 65,\n" +
                        "    \"brand\": \"Impression of Acqua Di Gio\",\n" +
                        "    \"category\": \"fragrances\",\n" +
                        "    \"thumbnail\": \"https://i.dummyjson.com/data/products/11/thumnail.jpg\"\n" +
                        "}")
                .when()
                .post("/products/add")
                .then()
                .assertThat().statusCode(400);
    }

    @Test
    @Story("PostTestProductsADD")
    @Description("Os produtos criados não serão salvos, devemos verificar se o produto não existe")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void getProductsNotSaved() {
        RestAssured.baseURI = baseUrl;
        given()
                .when()
                .get("/products/101")
                .then()
                .statusCode(404);

    }
}
