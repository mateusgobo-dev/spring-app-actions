package br.spring.git.actions.api.functions;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.web.dto.UsuarioDto;
import br.spring.git.actions.web.mappers.UsuarioMapperImpl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface MappersFn {

    Function<UsuarioDto, Usuario> usuarioSerialize = usuarioDto -> new UsuarioMapperImpl().toEntity(usuarioDto);
    Function<Usuario, UsuarioDto> usuarioDeserialize = usuarioEntity -> new UsuarioMapperImpl().toDto(usuarioEntity);

    Function<List<Usuario>,List<UsuarioDto>> collectionUsuarioDeserialize = collectionUsuario -> collectionUsuario.stream().map(usuario -> usuarioDeserialize.apply(usuario)).collect(Collectors.toList());

}
