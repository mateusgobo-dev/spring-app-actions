package br.spring.git.actions.web.mappers;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.web.dto.UsuarioDto;
import org.mapstruct.Mapper;

@Mapper
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDto usuarioDto);
    UsuarioDto toDto(Usuario usuario);
}
