package api;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import org.testng.annotations.Test;
import java.io.InputStream;
import java.util.Properties;


@Epic("Testes de API")
@Feature("Testes da API GET/test")
public class GetApiTest {
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
    String URL = baseUrl;

    @Test
    @Description("Verifica se a API de teste está acessível e retorna o status esperado.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testeBemSucedidoAPI() {
        Response response = acessarAPI();
        validarRespostaSucesso(response);
    }

    @Test
    @Description("Verifica o comportamento quando a API de teste não está disponível.")
    @Owner("Lucas Di Blasi")
    @Issue("Desafio API")
    public void testeMalSucedidoAPI() {
        // Simulando a API de teste não estar funcionando
        RestAssured.baseURI = URL;
        Response response = acessarAPI();
        validarRespostaErro(response);
    }

    @Step("Acessando a API")
    private Response acessarAPI() {
        RestAssured.baseURI = URL;
        return given()
                .when()
                .get("/test")
                .then()
                .extract()
                .response();
    }

    @Step("Validando resposta sucesso")
    private void validarRespostaSucesso(Response response) {
        response.then()
                .statusCode(200)
                .body("status", equalTo("ok"));
    }

    @Step("Validando resposta não deve ser 404")
    private void validarRespostaErro(Response response) {
        response.then()
                .assertThat()
                .statusCode(not(equalTo(404)));
    }
}
