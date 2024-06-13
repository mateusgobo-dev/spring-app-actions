package br.spring.git.actions.api.repository;

import br.spring.git.actions.api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNome(String nome);

    Usuario findByEmail(String email);
}
