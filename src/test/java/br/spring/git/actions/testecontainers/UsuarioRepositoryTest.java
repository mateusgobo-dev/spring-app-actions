package br.spring.git.actions.testecontainers;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.api.services.UsuarioService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioService usuarioService;

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    public static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    public static void afterAll() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testContainersCheck() {
        System.out.println("Initialzed...");
    }

    @Order(1)
    @Test
    public void salvarUsuario() {
        this.usuarioService.save(Usuario.builder().nome("Teste1").email("teste@teste.com.br").senha(UUID.randomUUID().toString()).build());
    }

    @Order(2)
    @Test
    public void consultarUsuario() {
        List<Usuario> usuarioCollection = this.usuarioService.findAll();
        usuarioCollection.stream().forEach(System.out::println);
    }
}
