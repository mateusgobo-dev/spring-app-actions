package br.spring.git.actions.api.controller;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.api.exceptions.UsuarioException;
import br.spring.git.actions.api.repository.BaseTest;
import br.spring.git.actions.api.repository.UsuarioDao;
import br.spring.git.actions.api.repository.UsuarioRepository;
import br.spring.git.actions.api.services.UsuarioService;
import br.spring.git.actions.web.controllers.UsuarioController;
import br.spring.git.actions.web.dto.UsuarioDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static br.spring.git.actions.api.functions.MappersFn.usuarioSerialize;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


@TestMethodOrder(OrderAnnotation.class)
@Slf4j
public class UsuarioControllerTest extends BaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioDao usuarioDao;
    private UsuarioController usuarioController;

    private ResponseEntity salvarRegistro(UsuarioDto usuarioDto) throws UsuarioException {
        this.validarConteudoUsuarioDto(usuarioDto);
        Usuario usuario = usuarioSerialize.apply(usuarioDto);
        when(this.usuarioRepository.save(usuario)).thenReturn(usuario);

        ResponseEntity responseEntity = this.usuarioController.salvarUsuario(usuarioDto);
        log.info(responseEntity.getBody().toString());

        verify(this.usuarioRepository, times(1)).save(usuario);
        return responseEntity;
    }

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.usuarioService = new UsuarioService(this.usuarioDao);
        this.usuarioController = new UsuarioController(this.usuarioService);
    }

    @Order(1)
    @Test
    public void buscarTodosRegistrosTest_Success() {
        when(this.usuarioRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity response = this.usuarioController.buscarTodosRegistros();
        System.out.println(response.getBody().toString());

        verify(this.usuarioRepository, times(1)).findAll();
    }

    @Order(2)
    @Test
    public void salvarUsuarioTest_Success() throws UsuarioException {
        UsuarioDto usuarioDto = this.usuarioDto();
        ResponseEntity response = this.salvarRegistro(usuarioDto);

        Assertions.assertEquals(true, response.getBody().toString().equals("Usuario criado com sucesso."));
    }

    @Order(3)
    @Test
    public void salvarUsuarioTest_Error_Nome() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setNome("");

            ResponseEntity response = this.salvarRegistro(usuarioDto);
            Assertions.assertEquals(true, response.getBody().toString().equals("Usuario criado com sucesso."));
        }, messageVazio.formatted("Nome"));
    }

    @Order(4)
    @Test
    public void salvarUsuarioTest_ErrorNulo_Nome() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setNome(null);

            ResponseEntity response = this.salvarRegistro(usuarioDto);
            Assertions.assertEquals(true, response.getBody().toString().equals("Usuario criado com sucesso."));
        }, messageNulo.formatted("Nome"));
    }

    @Order(5)
    @Test
    public void salvarUsuarioTest_Error_Email() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setEmail("");

            ResponseEntity response = this.salvarRegistro(usuarioDto);
            Assertions.assertEquals(true, response.getBody().toString().equals("Usuario criado com sucesso."));
        }, messageVazio.formatted("Email"));
    }

    @Order(6)
    @Test
    public void salvarUsuarioTest_ErrorNulo_Email() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setEmail(null);

            ResponseEntity response = this.salvarRegistro(usuarioDto);
            Assertions.assertEquals(true, response.getBody().toString().equals("Usuario criado com sucesso."));
        }, messageNulo.formatted("Email"));
    }

    @Order(7)
    @Test
    public void salvarUsuarioTest_Error_Senha() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setSenha("");

            ResponseEntity response = this.salvarRegistro(usuarioDto);
            Assertions.assertEquals(true, response.getBody().toString().equals("Usuario criado com sucesso."));
        }, messageVazio.formatted("Senha"));
    }

    @Order(8)
    @Test
    public void salvarUsuarioTest_ErrorNulo_Senha() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            UsuarioDto usuarioDto = this.usuarioDto();
            usuarioDto.setSenha(null);

            ResponseEntity response = this.salvarRegistro(usuarioDto);
            Assertions.assertEquals(true, response.getBody().toString().equals("Usuario criado com sucesso."));
        }, messageNulo.formatted("Senha"));
    }

    @Order(9)
    @Test
    public void deletarUsuarioTest_Success() throws UsuarioException {
        Usuario usuario = Usuario.builder().build();
        usuario.setId(1L);

        ResponseEntity response = this.usuarioController.excluirUsuario(usuario.getId());
        log.info(response.getBody().toString());

        verify(this.usuarioRepository, times(1)).delete(usuario);
        Assertions.assertEquals(true, response.getBody().toString().equals("Registro removido com sucesso"));
    }
}
