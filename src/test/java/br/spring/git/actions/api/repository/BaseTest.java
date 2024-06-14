package br.spring.git.actions.api.repository;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.api.exceptions.UsuarioException;
import br.spring.git.actions.web.dto.UsuarioDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Set;

import static br.spring.git.actions.api.functions.MappersFn.usuarioDeserialize;

public class BaseTest {

    protected static final String messageNulo = "%s nao pode ser nulo";
    protected static final String messageVazio = "%s tem preenchimento obrigatorio";

    protected final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected final Validator validator = factory.getValidator();
    protected final Validator validatorDto = factory.getValidator();

    protected Usuario usuario() {
        return Usuario.builder()
                .nome("TDD NA PRATICA")
                .email("tdd@tdd.com.br")
                .senha(Base64.getEncoder().encodeToString("tdd2024".getBytes(Charset.forName("UTF-8")))).build();
    }

    protected UsuarioDto usuarioDto() {
        return usuarioDeserialize.apply(this.usuario());
    }

    protected boolean validarConteudoUsuario(Usuario usuario) throws UsuarioException {
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        if (!violations.isEmpty()) {
            throw new UsuarioException(violations.stream().iterator().next().getMessage());
        }
        return true;
    }

    protected boolean validarConteudoUsuarioDto(UsuarioDto usuarioDto) throws UsuarioException {
        Set<ConstraintViolation<UsuarioDto>> violations = validatorDto.validate(usuarioDto);
        if (!violations.isEmpty()) {
            throw new UsuarioException(violations.stream().iterator().next().getMessage());
        }
        return true;
    }
}
