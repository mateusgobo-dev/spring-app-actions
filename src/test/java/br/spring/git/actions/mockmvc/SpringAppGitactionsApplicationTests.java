package br.spring.git.actions.mockmvc;

import br.spring.git.actions.api.repository.BaseTest;
import br.spring.git.actions.api.repository.UsuarioRepository;
import br.spring.git.actions.web.dto.UsuarioDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static br.spring.git.actions.api.functions.ParserFn.toJson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class SpringAppGitactionsApplicationTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(SpringAppGitactionsApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final String messageError = "%s tem preenchimento obrigatorio";
    private final String conteudo = "Response: %s";


    public void limparBase(){
        log.info("Limpando a base para o teste");
        this.usuarioRepository.deleteAll();
    }

    @AfterEach
    public void afterEach() {
        log.info("Teste executado...");
    }

    @Order(1)
    @Test
    public void salvarUsuario() {
        try {
            this.limparBase();
            UsuarioDto usuarioDto = this.usuarioDto();//Cenário

            MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/usuario"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson.apply(usuarioDto)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse();
            String result = response.getContentAsString();
            String[] header = response.getHeader("registro").split("\\/");
            Long idDevolvido = Long.parseLong(header[header.length - 1]);
            log.info("Id criado %s".formatted(idDevolvido));

            Assertions.assertEquals("Usuario criado com sucesso.", result);
        } catch (Exception e) {
            Assertions.fail("Erro na execucao do teste. [Erro] %s".formatted(e.getMessage()));
        }
    }

    @Order(2)
    @Test
    public void buscarTodosRegistros(){
        try{
            MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/usuario")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
            String value = response.getContentAsString();
            log.info(conteudo.formatted(value));
            Assertions.assertEquals(true, value != null, "Requisicao processada com sucesso.");
        }catch (Exception e){
            Assertions.fail("Erro na execucao do teste \"buscarTodosRegistros()\", [Erro] %s".formatted(e.getMessage()));
        }
    }

    @Order(3)
    @Test
    public void buscarPeloNome(){
        try{
            MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/usuario/nome/TDD NA PRATICA")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
            String value = response.getContentAsString();
            log.info(conteudo.formatted(value));
            Assertions.assertEquals(true, value != null, "Requisicao processada com sucesso.");
        }catch (Exception e){
            Assertions.fail("Erro na execucao do teste \"buscarTodosRegistros()\", [Erro] %s".formatted(e.getMessage()));
        }
    }

    @Order(4)
    @Test
    public void buscarPeloEmail(){
        try{
            MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/usuario/email/tdd@tdd.com.br")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
            String value = response.getContentAsString();
            log.info(conteudo.formatted(value));
            Assertions.assertEquals(true, value != null, "Requisicao processada com sucesso.");
        }catch (Exception e){
            Assertions.fail("Erro na execucao do teste \"buscarTodosRegistros()\", [Erro] %s".formatted(e.getMessage()));
        }
    }

    @Order(5)
    @Test
    public void salvarUsuarioSemNome_Error() {
        try {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setNome("");//Cenário.
            String value = this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/usuario"))//Acao
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson.apply(usuarioDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();
            log.info(value);
            Assertions.assertEquals(messageError.formatted("Nome"), value);
        } catch (Exception e) {
            Assertions.fail("Erro na execucao do teste. [Erro] %s".formatted(e.getMessage()));
        }
    }

    @Order(6)
    @Test
    public void salvarUsuarioSemEmail_Error() {
        try {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setEmail("");//Cenário.
            String value = this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/usuario"))//Acao
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson.apply(usuarioDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();
            log.info(value);
            Assertions.assertEquals(messageError.formatted("Email"), value);
        } catch (Exception e) {
            Assertions.fail("Erro na execucao do teste. [Erro] %s".formatted(e.getMessage()));
        }
    }

    @Order(7)
    @Test
    public void salvarUsuarioSemSenha_Error() {
        try {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setSenha("");//Cenário.
            String value = this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/usuario"))//Acao
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson.apply(usuarioDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();
            log.info(value);
            Assertions.assertEquals(messageError.formatted("Senha"), value);
        } catch (Exception e) {
            Assertions.fail("Erro na execucao do teste. [Erro] %s".formatted(e.getMessage()));
        }
    }
}
