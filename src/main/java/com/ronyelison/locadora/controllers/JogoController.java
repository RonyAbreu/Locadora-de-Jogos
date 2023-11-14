package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.entities.Jogo;
import com.ronyelison.locadora.services.JogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = JogoDTO.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de usuário", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Proibido acesso", responseCode = "403", content = @Content)
            })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JogoDTO> adicionarJogo(@RequestBody @Valid JogoDTO jogoDTO){
        var jogo = service.adicionarJogo(jogoDTO);
        return ResponseEntity.ok(jogo);
    }

    @Operation(summary = "Endpoint para retornar todos os jogos", description = "Endpoint para retornar todos os jogos", tags = "Jogo",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = JogoDTO.class)))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Proibido acesso", responseCode = "403", content = @Content)
            })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<JogoDTO>> retornaTodosOsJogos(){
        var listaDeJogos = service.retornarTodosOsJogos();
        return ResponseEntity.ok(listaDeJogos);
    }

    @Operation(summary = "Endpoint para retornar um jogo", description = "Endpoint para retornar um jogo", tags = "Jogo",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = JogoDTO.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Proibido acesso", responseCode = "403", content = @Content)
            })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JogoDTO> retornaJogoPeloId(@PathVariable Long id){
        var jogoRetornado = service.retornaJogoPeloId(id);
        return ResponseEntity.ok(jogoRetornado);
    }

    @Operation(summary = "Endpoint para deletar um jogo", description = "Endpoint para deletar um jogo", tags = "Jogo",
            responses = {
                    @ApiResponse(description = "Sem retorno", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Proibido acesso", responseCode = "403", content = @Content)
            })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeJogoPeloId(@PathVariable Long id){
        service.removeJogoPeloId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Endpoint para atualizar um jogo", description = "Endpoint para atualizar um jogo", tags = "Jogo",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = JogoDTO.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de usuário", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Proibido acesso", responseCode = "403", content = @Content)
            })
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JogoDTO> atualizaJogo(@PathVariable Long id, @RequestBody @Valid JogoDTO jogoDTO){
        var jogoAtualizado = service.atualizaJogo(id,jogoDTO);
        return ResponseEntity.ok(jogoAtualizado);
    }

    @Operation(summary = "Endpoint para retornar jogos pelo nome", description = "Endpoint para retornar jogos pelo nome", tags = "Jogo",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = JogoDTO.class)))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de usuário", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Proibido acesso", responseCode = "403", content = @Content)
            })
    @GetMapping(value = "/nome", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<JogoDTO>> retornaJogosPeloNome(@RequestParam(value = "nome", defaultValue = "n") String nome){
        var jogoRetornado = service.retornaJogosPeloNome(nome);
        return ResponseEntity.ok(jogoRetornado);
    }
}
