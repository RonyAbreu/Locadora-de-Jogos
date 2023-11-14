package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.usuario.TokenDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDeLogin;
import com.ronyelison.locadora.dto.usuario.UsuarioDeRegistroDTO;
import com.ronyelison.locadora.entities.Jogo;
import com.ronyelison.locadora.entities.Usuario;
import com.ronyelison.locadora.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;

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
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@RequestBody UsuarioDeRegistroDTO usuarioDeRegistro){
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
    public ResponseEntity<TokenDTO> login(@RequestBody UsuarioDeLogin usuarioDeLogin){
        var token = service.login(usuarioDeLogin);
        return ResponseEntity.ok(token);
    }
}
