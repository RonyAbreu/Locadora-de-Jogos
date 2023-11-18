package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.usuario.TokenDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDeLogin;
import com.ronyelison.locadora.dto.usuario.UsuarioDeRegistroDTO;
import com.ronyelison.locadora.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário", description = "Endpoint de Usuários")
public class UsuarioController {
    private UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.service = usuarioService;
    }

    @Operation(summary = "Endpoint para cadastrar um Usuário", description = "Endpoint para cadastrar um Usuário", tags = "Usuário",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
                    @ApiResponse(description = "Erro do usuário", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Conflito", responseCode = "409", content = @Content)
            })
    @PostMapping(value = "/registro",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@RequestBody @Valid UsuarioDeRegistroDTO usuarioDeRegistro){
        var usuario = service.cadastraUsuario(usuarioDeRegistro);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Endpoint para fazer login", description = "Endpoint para fazer login", tags = "Usuário",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenDTO.class))),
                    @ApiResponse(description = "Erro do usuário", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
            })
    @PostMapping(value = "/login",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UsuarioDeLogin usuarioDeLogin){
        var token = service.login(usuarioDeLogin);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Endpoint para fazer atualizar token", description = "Endpoint para fazer atualizar token", tags = "Usuário",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenDTO.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
            })
    @PutMapping(value = "/refresh/{email}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TokenDTO> atualizaToken(@PathVariable String email, @RequestHeader("Authorization") String refreshToken){
        var token = service.atualizaToken(email, refreshToken);
        return ResponseEntity.ok(token);
    }
}
