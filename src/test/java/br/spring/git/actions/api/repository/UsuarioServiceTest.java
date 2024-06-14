package br.spring.git.actions.api.repository;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.api.exceptions.UsuarioException;
import br.spring.git.actions.api.services.UsuarioService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.Mock;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@Profile("!default")
@TestMethodOrder(OrderAnnotation.class)
class UsuarioServiceTest extends BaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;
    private UsuarioDao usuarioDao;

    @BeforeEach
    void beforeEach() {
        openMocks(this);
        this.usuarioDao = new UsuarioDao(this.usuarioRepository);
        this.usuarioService = new UsuarioService(this.usuarioDao);
    }

    private void salvarUsuario(Usuario usuario) throws UsuarioException {
        try {
            //Cenario
            this.validarConteudoUsuario(usuario);
            when(usuarioRepository.save(usuario)).thenReturn(usuario);

            //Ação
            Usuario usuarioSalvo = this.usuarioService.save(usuario);

            //Verificacao
            verify(usuarioRepository, times(1)).save(usuario);
            Assertions.assertEquals(true, usuario.equals(usuarioSalvo), "Usuario criado com sucesso.");
        } catch (UsuarioException ex) {
            throw new UsuarioException("Falha ao salvar usuario. [Erro] = %s".formatted(ex.getMessage()));
        }
    }

    @Order(1)
    @Test
    public void salvarUsuarioTest_Error_SemNome() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            Usuario usuario = this.usuario();
            usuario.setNome(null);
            this.salvarUsuario(usuario);
        }, messageNulo.formatted("Nome"));
    }

    @Order(2)
    @Test
    public void salvarUsuarioTest_Error_NomeSemPreenchimento() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            Usuario usuario = this.usuario();
            usuario.setNome("");
            this.salvarUsuario(usuario);
        }, messageVazio.formatted("Nome"));
    }

    @Order(3)
    @Test
    public void salvarUsuarioTest_Error_SenhaNulo() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            Usuario usuario = this.usuario();
            usuario.setSenha("");
            this.salvarUsuario(usuario);
        }, messageNulo.formatted("Senha"));
    }

    @Order(4)
    @Test
    public void salvarUsuarioTest_Error_SenhaPreenchimento() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            Usuario usuario = this.usuario();
            usuario.setSenha("");
            this.salvarUsuario(usuario);
        }, messageVazio.formatted("Senha"));
    }

    @Order(5)
    @Test
    public void salvarUsuarioTest_Error_EmailNulo() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            Usuario usuario = this.usuario();
            usuario.setEmail(null);
            this.salvarUsuario(usuario);
        }, messageNulo.formatted("Email"));
    }

    @Order(6)
    @Test
    public void salvarUsuarioTest_Error_EmailPreenchimento() {
        Assertions.assertThrows(UsuarioException.class, () -> {
            Usuario usuario = this.usuario();
            usuario.setEmail(null);
            this.salvarUsuario(usuario);
        }, messageVazio.formatted("Email"));
    }

    @Order(7)
    @Test
    public void salvarUsuarioTest_Success() throws UsuarioException {
        this.salvarUsuario(this.usuario());
    }

    @Order(8)
    @Test
    public void excluirUsuarioTest_Success() {
        try {
            //Cenario
            Usuario usuario = this.usuario();
            usuario.setId(1L);

            //Acao
            String message = this.usuarioService.delete(usuario);

            //Verificacao
            verify(this.usuarioRepository, times(1)).delete(usuario);
            Assertions.assertEquals("Usuario removido com sucesso", message);
        } catch (UsuarioException ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    @Order(9)
    @Test
    public void consultarUsuarioPorNomeTest_Success() {
        Usuario usuario = this.usuario();
        when(this.usuarioRepository.findByNome(usuario.getNome())).thenReturn(new ArrayList<>());

        List<Usuario> usuarioCollection = this.usuarioService.findByNome(usuario);

        verify(this.usuarioRepository, times(1)).findByNome(usuario.getNome());
        Assertions.assertEquals(true, usuarioCollection != null, "Usuarios carregados.");
    }

    @Order(10)
    @Test
    public void consultarUsuarioPorEmailTest_Success() {
        Usuario usuario = this.usuario();
        when(this.usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        Usuario usuarioByEmail = this.usuarioService.findByEmail(usuario);

        verify(this.usuarioRepository, times(1)).findByEmail(usuario.getEmail());
        Assertions.assertEquals(true, usuarioByEmail.equals(usuario), "Usuarios identificado por email");
    }


    @Order(10)
    @Test
    public void consultarTodosUsuarios_Success() {
        when(this.usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        List<Usuario> usuarioCollection = this.usuarioService.findAll();

        verify(this.usuarioRepository, times(1)).findAll();
        Assertions.assertEquals(true, usuarioCollection != null, "Usuarios carregados com sucesso.");
    }
}
