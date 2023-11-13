package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.usuario.TokenDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDeLogin;
import com.ronyelison.locadora.dto.usuario.UsuarioDeRegistroDTO;
import com.ronyelison.locadora.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.service = usuarioService;
    }

    @PostMapping(value = "/registro")
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@RequestBody UsuarioDeRegistroDTO usuarioDeRegistro){
        var usuario = service.cadastraUsuario(usuarioDeRegistro);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UsuarioDeLogin usuarioDeLogin){
        var token = service.login(usuarioDeLogin);
        return ResponseEntity.ok(token);
    }
}
