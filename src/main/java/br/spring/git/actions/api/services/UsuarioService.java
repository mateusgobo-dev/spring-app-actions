package br.spring.git.actions.api.services;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.api.exceptions.UsuarioException;
import br.spring.git.actions.api.repository.UsuarioDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UsuarioDao usuarioDao;


    public Usuario save(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    public String delete(Usuario usuario) throws UsuarioException {
        return usuarioDao.delete(usuario);
    }

    public List<Usuario> findByNome(Usuario usuario) {
        return usuarioDao.findByNome(usuario);
    }

    public Usuario findByEmail(Usuario usuario) {
        return usuarioDao.findByEmail(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }
}
