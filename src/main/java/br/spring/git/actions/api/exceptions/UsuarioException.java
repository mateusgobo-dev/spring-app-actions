package br.spring.git.actions.api.exceptions;

import lombok.Getter;

import java.util.Set;

@Getter
public class UsuarioException extends Exception {


    public UsuarioException(String message) {
        super(message);
    }
}
