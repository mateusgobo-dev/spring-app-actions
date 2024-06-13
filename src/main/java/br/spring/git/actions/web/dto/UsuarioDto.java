package br.spring.git.actions.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto implements Serializable {
    private final static long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Nome tem preenchimento obrigatorio")
    @NotNull(message = "Nome nao pode ser nulo")
    private String nome;

    @NotBlank(message = "Email tem preenchimento obrigatorio")
    @NotNull(message = "Email nao pode ser nulo")
    private String email;

    @NotBlank(message = "Senha tem preenchimento obrigatorio")
    @NotNull(message = "Senha nao pode ser nulo")
    private String senha;
}
