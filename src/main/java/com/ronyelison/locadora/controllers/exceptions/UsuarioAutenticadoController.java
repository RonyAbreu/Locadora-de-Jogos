package com.ronyelison.locadora.controllers.exceptions;

import com.ronyelison.locadora.dto.usuario.UsuarioAtualizado;
import com.ronyelison.locadora.dto.usuario.UsuarioDTO;
import com.ronyelison.locadora.entities.Usuario;
import com.ronyelison.locadora.services.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UsuarioAutenticadoController {
    private UsuarioAutenticadoService autenticadoService;

    @Autowired
    public UsuarioAutenticadoController(UsuarioAutenticadoService autenticadoService) {
        this.autenticadoService = autenticadoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> retornaUsuarioPeloId(@PathVariable Long id){
        var usuario = autenticadoService.retornaUsuarioPeloId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> atualizaUsuario(@PathVariable Long id, @RequestBody UsuarioAtualizado usuarioAtualizado){
        var usuario = autenticadoService.atualizaUsuario(id,usuarioAtualizado);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeUsuarioPeloId(@PathVariable Long id){
        autenticadoService.removeUsuarioPeloId(id);
        return ResponseEntity.noContent().build();
    }
}
