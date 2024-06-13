package br.spring.git.actions.restassured;

import br.spring.git.actions.api.repository.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static io.restassured.RestAssured.given;

@Profile("test")
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class SpringAppGitactionsApplicationTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(SpringAppGitactionsApplicationTests.class);
    private final String message = "Requisicao processada com sucesso %s.";
    private final String conteudo = "Response: %s";

    @AfterEach
    public void afterEach() {
        log.info("Teste executado...");
    }

    @Order(1)
    @Test
    public void buscarTodosRegistros_Success() {
        try {
            given()
                    .when()
                    .get("http://localhost:8080/api/v1/usuario")
                    .then()
                    .statusCode(201);
        } catch (Exception e) {
            Assertions.fail("Erro na execucao do teste \"buscarTodosRegistros()\", [Erro] %s".formatted(e.getMessage()));
        }
    }
}
