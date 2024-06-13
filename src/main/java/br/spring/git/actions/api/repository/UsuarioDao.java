package br.spring.git.actions.api.repository;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.api.exceptions.UsuarioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsuarioDao {

    private final UsuarioRepository usuarioRepository;

    public Usuario save(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public String delete(Usuario usuario) throws UsuarioException {
        try {
            this.usuarioRepository.delete(usuario);
            return "Usuario removido com sucesso";
        } catch (Exception ex) {
            throw new UsuarioException("Erro ao excluir usuario.");
        }
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findByNome(Usuario usuario) {
        return this.usuarioRepository.findByNome(usuario.getNome());
    }

    public Usuario findByEmail(Usuario usuario) {
        return this.usuarioRepository.findByEmail(usuario.getEmail());
    }
}
