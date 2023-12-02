package com.ronyelison.locadora.controllers;

import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.services.JogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/jogos")
@Tag(name = "Jogo", description = "Locadora de Jogos")
public class JogoController {
    private Logger logger = Logger.getLogger(JogoController.class.getName());
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
        logger.info("Adicionando jogo...");
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
    public ResponseEntity<PagedModel<EntityModel<JogoDTO>>> retornaTodosOsJogos(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                                               @RequestParam(value = "limite", defaultValue = "12") Integer limite,
                                                                               @RequestParam(value = "direcao", defaultValue = "asc") String direcao){
        logger.info("Retornando todos os jogos...");
        var direcaoDaPagina = "desc".equalsIgnoreCase(direcao) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pagina, limite, Sort.by(direcaoDaPagina, "nome"));
        var listaDeJogos = service.retornarTodosOsJogos(pageable);
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
        logger.info("Retornando um jogo...");
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
        logger.info("Removendo um jogo...");
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
        logger.info("Atualizando um jogo...");
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
    @GetMapping(value = "/nome/{nomeJogo}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PagedModel<EntityModel<JogoDTO>>> retornaJogosPeloNome(@PathVariable String nomeJogo, @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                              @RequestParam(value = "limite", defaultValue = "12") Integer limite, @RequestParam(value = "ordem", defaultValue = "asc") String ordem){
        logger.info("Retornando um jogo pelo nome...");
        var ordenacao = "desc".equalsIgnoreCase(ordem) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pagina,limite, Sort.by(ordenacao,"nome"));
        var jogoRetornado = service.retornaJogosPeloNome(nomeJogo,pageable);
        return ResponseEntity.ok(jogoRetornado);
    }

    @Operation(summary = "Endpoint para desativar um jogo", description = "Endpoint para desativar um jogo", tags = "Jogo",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = JogoDTO.class))),
                    @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro de servidor", responseCode = "500", content = @Content),
                    @ApiResponse(description = "Proibido acesso", responseCode = "403", content = @Content)
            })
    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<JogoDTO> desativaJogoPeloId(@PathVariable Long id){
        logger.info("Desativando um jogo...");
        var jogoRetornado = service.desativaJogo(id);
        return ResponseEntity.ok(jogoRetornado);
    }
}
