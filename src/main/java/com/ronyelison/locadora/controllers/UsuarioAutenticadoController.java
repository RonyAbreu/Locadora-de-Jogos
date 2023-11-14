package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.usuario.UsuarioAtualizado;
import com.ronyelison.locadora.dto.usuario.UsuarioDTO;
import com.ronyelison.locadora.entities.Usuario;
import com.ronyelison.locadora.services.UsuarioAutenticadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoint para usuários autenticados")
public class UsuarioAutenticadoController {
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    public UsuarioAutenticadoController(UsuarioAutenticadoService autenticadoService) {
        this.autenticadoService = autenticadoService;
    }

    @Operation(summary = "Endpoint para retornar um Usuário pelo Id", description = "Endpoint para retornar um Usuário pelo Id", tags = "Autenticação",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = Usuario.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content)
            })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Usuario> retornaUsuarioPeloId(@PathVariable Long id){
        var usuario = autenticadoService.retornaUsuarioPeloId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Endpoint para atualizar um Usuário", description = "Endpoint para atualizar um Usuário", tags = "Autenticação",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = Usuario.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro do usuário", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content)
            })
    @PutMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UsuarioDTO> atualizaUsuario(@PathVariable Long id, @RequestBody UsuarioAtualizado usuarioAtualizado){
        var usuario = autenticadoService.atualizaUsuario(id,usuarioAtualizado);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Endpoint para deletar um Usuário", description = "Endpoint para deletar um Usuário", tags = "Autenticação",
            responses = {
                    @ApiResponse(description = "Sem retorno", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content)
            })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeUsuarioPeloId(@PathVariable Long id){
        autenticadoService.removeUsuarioPeloId(id);
        return ResponseEntity.noContent().build();
    }
}
