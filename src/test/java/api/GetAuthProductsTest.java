package api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Properties;

@Epic("Testes de API")
@Feature("Obter lista de produtos")
public class GetAuthProductsTest {

    public static Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream input = GetApiTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    static String baseUrl = loadProperties().getProperty("BASE_URL");
    static String username = loadProperties().getProperty("USERNAME");
    static String password = loadProperties().getProperty("PASSWORD");
    private static String authToken;

    static String requestBody = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = baseUrl;
        authToken = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("token");
    }

    @Test
    @Description("Verifica se é possível obter a lista de produtos autenticado.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testObterListaDeProdutosAposAutenticacao() {
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/auth/products")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("size()", equalTo(4));
    }

    @Test
    @Description("Verifica o comportamento quando o usuário não está autenticado.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testeFalhoTokenInvalido() {
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + "ONIqfR72x38xG5BSrgt9FaejEkvy3Tu63Nvi8uFSsMb2HzRxo0BWS9Jx665d7cc3d6179a" )
                .when()
                .get("/auth/products")
                .then()
                .assertThat().statusCode(401)
                .body("name", equalTo("JsonWebTokenError"))
                .body("message", equalTo("Invalid/Expired Token!"));
    }

    @Test
    @Description("Verifica o comportamento quando há problema na autenticação")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testAutenticacaoFalho() {
        given()
                .auth().basic("lucasdiblasi", "0lelplR")
                .when()
                .get("/auth/products")
                .then()
                .assertThat().statusCode(403)
                .body("message", equalTo("Authentication Problem"));
    }
}
