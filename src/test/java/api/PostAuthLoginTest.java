package api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import java.io.InputStream;
import java.util.Properties;

@Epic("Testes de API")
@Feature("Login de Usuário")
public class PostAuthLoginTest {

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

    static String requestBody = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

    @Test
    @Description("Verifica se é possível fazer login com credenciais válidas.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void efetuarLoginComSucesso() {
        RestAssured.baseURI = baseUrl;
        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    @Description("Verifica o comportamento quando as credenciais de login são inválidas.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testeLoginFalhoComCredenciaisInvalidas() {
        RestAssured.baseURI = baseUrl;
        given()
                .contentType("application/json")
                .body("{\"username\": \"lucasdiblasi\", \"password\": \"senha\"}")
                .when()
                .post("/auth/login")
                .then()
                .assertThat().statusCode(400); //
    }
}
