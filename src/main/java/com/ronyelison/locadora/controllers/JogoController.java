package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.entities.Jogo;
import com.ronyelison.locadora.services.JogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jogos")
@Tag(name = "Jogo", description = "Locadora de Jogos")
public class JogoController {
    private JogoService service;

    @Autowired
    public JogoController(JogoService service) {
        this.service = service;
    }

    @Operation(summary = "Endpoint para adicionar jogos", description = "Endpoint para adicionar jogos", tags = "Jogo",
            responses = @ApiResponse())
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JogoDTO> adicionarJogo(@RequestBody @Valid JogoDTO jogoDTO){
        var jogo = service.adicionarJogo(jogoDTO);
        return ResponseEntity.ok(jogo);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Jogo>> retornaTodosOsJogos(){
        var listaDeJogos = service.retornarTodosOsJogos();
        return ResponseEntity.ok(listaDeJogos);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Jogo> retornaJogoPeloId(@PathVariable Long id){
        var jogoRetornado = service.retornaJogoPeloId(id);
        return ResponseEntity.ok(jogoRetornado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeJogoPeloId(@PathVariable Long id){
        service.removeJogoPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JogoDTO> atualizaJogo(@PathVariable Long id, @RequestBody @Valid JogoDTO jogoDTO){
        var jogoAtualizado = service.atualizaJogo(id,jogoDTO);
        return ResponseEntity.ok(jogoAtualizado);
    }

    @GetMapping(value = "/nome", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Jogo> retornaJogoPeloNome(@RequestParam(value = "nome", defaultValue = "n") String nome){
        var jogoRetornado = service.retornaJogoPeloNome(nome);
        return ResponseEntity.ok(jogoRetornado);
    }
}
