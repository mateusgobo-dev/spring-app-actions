package br.spring.git.actions.web.mappers;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.web.dto.UsuarioDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T09:21:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Ubuntu)"
)
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioDto usuarioDto) {
        if ( usuarioDto == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.id( usuarioDto.getId() );
        usuario.nome( usuarioDto.getNome() );
        usuario.email( usuarioDto.getEmail() );
        usuario.senha( usuarioDto.getSenha() );

        return usuario.build();
    }

    @Override
    public UsuarioDto toDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDto.UsuarioDtoBuilder usuarioDto = UsuarioDto.builder();

        usuarioDto.id( usuario.getId() );
        usuarioDto.nome( usuario.getNome() );
        usuarioDto.email( usuario.getEmail() );
        usuarioDto.senha( usuario.getSenha() );

        return usuarioDto.build();
    }
}
