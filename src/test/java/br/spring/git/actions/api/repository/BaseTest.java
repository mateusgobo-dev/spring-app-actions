package br.spring.git.actions.api.repository;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.web.dto.UsuarioDto;

import java.nio.charset.Charset;
import java.util.Base64;

import static br.spring.git.actions.api.functions.MappersFn.*;

public class BaseTest {

    protected Usuario usuario() {
        return Usuario.builder()
                .nome("TDD NA PRATICA")
                .email("tdd@tdd.com.br")
                .senha(Base64.getEncoder().encodeToString("tdd2024".getBytes(Charset.forName("UTF-8")))).build();
    }

    protected UsuarioDto usuarioDto(){
        return usuarioDeserialize.apply(this.usuario());
    }
}
