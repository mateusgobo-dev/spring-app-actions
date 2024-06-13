package br.spring.git.actions.web.controllers;

import br.spring.git.actions.api.domain.Usuario;
import br.spring.git.actions.api.exceptions.UsuarioException;
import br.spring.git.actions.api.services.UsuarioService;
import br.spring.git.actions.web.dto.UsuarioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.spring.git.actions.api.functions.MappersFn.*;
import static br.spring.git.actions.api.functions.ParserFn.toJson;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvarUsuario(@Validated @RequestBody UsuarioDto usuarioDto) {
        try {
            Usuario usuario = usuarioService.save(usuarioSerialize.apply(usuarioDto));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("registro", "/buscar/%s".formatted(usuario.getId()))
                    .body("Usuario criado com sucesso.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na requisicao. [Erro] %s".formatted(ex.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluirUsuario(@RequestParam Long id) throws UsuarioException {
        try {
            this.usuarioService.delete(Usuario.builder().id(id).build());
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body("Registro removido com sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario removido com sucesso. [Erro] %s".formatted(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity buscarTodosRegistros(){
        try {
            return ResponseEntity.ok().body(toJson.apply(collectionUsuarioDeserialize.apply(this.usuarioService.findAll())));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na requisicao /api/v1/usuario. [Erro] %s".formatted(ex.getMessage()));
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity procurarPeloNome(@PathVariable("nome") String nome) {
        try {
            return ResponseEntity.ok().body(toJson.apply(collectionUsuarioDeserialize.apply(this.usuarioService.findByNome(Usuario.builder().nome(nome).build()))));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro no path /nome/%s".formatted(nome)+". [Erro] %s".formatted(ex.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity procurarPeloEmail(@PathVariable("email") String email) {
        try {
            return ResponseEntity.ok().body(toJson.apply(usuarioDeserialize.apply(this.usuarioService.findByEmail(Usuario.builder().email(email).build()))));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro no path /email/%s".formatted(email)+". [Erro] %s".formatted(ex.getMessage()));
        }
    }
}
