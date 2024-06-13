package br.spring.git.actions.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USUARIO")
@Entity
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", initialValue = 1, allocationSize = 1)
public class Usuario implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "usuario_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Nome tem preenchimento obrigatorio")
    @NotNull(message = "Nome nao pode ser nulo")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email tem preenchimento obrigatorio")
    @NotNull(message = "Email nao pode ser nulo")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Senha tem preenchimento obrigatorio")
    @NotNull(message = "Senha nao pode ser nulo")
    @Column(nullable = false)
    private String senha;
}
