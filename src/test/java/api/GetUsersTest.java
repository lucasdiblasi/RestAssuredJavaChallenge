package api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Properties;

@Epic("Testes de API")
@Feature("Get Users Endpoint")
public class GetUsersTest {

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
    @Story("Successful retrieval of user list")
    @Description("This test verifies that the list of users is obtained successfully.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testeObterListaUsuariosSucesso() {
        RestAssured.baseURI = baseUrl;
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("users", not(empty())) // Verifica se a lista de usuários não está vazia
                .body("users.username", everyItem(not(emptyOrNullString()))) // Verifica se todos os usernames não são nulos ou vazios
                .body("users.password", everyItem(not(emptyOrNullString()))) // Verifica se todos os passwords não são nulos ou vazios
                .body("users.username", hasItem("hbingley1"));
    }

    @Test
    @Story("Failure to retrieve user list")
    @Description("This test verifies that the user list retrieval fails.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testeFalhaObterListaUsuarios() {
        RestAssured.baseURI = baseUrl;
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(not(equalTo(404))); // Verifica se o status não é 404
    }
}
