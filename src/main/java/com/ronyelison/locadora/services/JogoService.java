package com.ronyelison.locadora.services;

import com.ronyelison.locadora.controllers.JogoController;
import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.entities.Jogo;
import com.ronyelison.locadora.mapper.Mapeador;
import com.ronyelison.locadora.repositories.JogoRepository;
import com.ronyelison.locadora.services.exceptions.JogoJaExisteException;
import com.ronyelison.locadora.services.exceptions.JogoNaoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class JogoService {

    private JogoRepository repository;
    private PagedResourcesAssembler<JogoDTO> assembler;

    @Autowired
    public JogoService(JogoRepository repository, PagedResourcesAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    public JogoDTO adicionarJogo(JogoDTO jogo){
        Optional<Jogo> jogoRetornado = repository.findByNome(jogo.getNome());

        if (jogoRetornado.isPresent()){
            throw new JogoJaExisteException("Jogo já existe!");
        }

        Jogo jogoParaSalvar = Mapeador.converteObjeto(jogo, Jogo.class);

        JogoDTO jogoDTO = Mapeador.converteObjeto(repository.save(jogoParaSalvar),JogoDTO.class);
        adicionaMetodoRetornaJogoPeloId(jogoDTO);

        return jogoDTO;
    }

    public JogoDTO retornaJogoPeloId(Long id){
        Optional<Jogo> jogoRetornado = repository.findById(id);

        if (jogoRetornado.isEmpty()){
            throw new JogoNaoExisteException("Jogo não existe");
        }

        JogoDTO jogoDTO = Mapeador.converteObjeto(jogoRetornado.get(),JogoDTO.class);
        adicionaMetodoRetornaJogoPeloId(jogoDTO);

        return jogoDTO;
    }

    public void removeJogoPeloId(Long id){
        Optional<Jogo> jogoParaSerDeletado = repository.findById(id);

        if (jogoParaSerDeletado.isEmpty()){
            throw new JogoNaoExisteException("Jogo não existe");
        }

        repository.delete(jogoParaSerDeletado.get());
    }

    public JogoDTO atualizaJogo(Long id, JogoDTO jogoDTO){
        Optional<Jogo> jogoRetornado = repository.findById(id);

        if (jogoRetornado.isEmpty()){
            throw new JogoNaoExisteException("Jogo não existe");
        }

        atualizarDados(jogoRetornado.get(),jogoDTO);
        repository.save(jogoRetornado.get());

        JogoDTO jogoDtoHateoas = Mapeador.converteObjeto(jogoRetornado.get(),JogoDTO.class);
        adicionaMetodoRetornaJogoPeloId(jogoDtoHateoas);

        return jogoDtoHateoas;
    }

    private void atualizarDados(Jogo jogo, JogoDTO jogoDTO) {
        jogo.setNome(jogoDTO.getNome());
        jogo.setGenero(jogoDTO.getGenero());
        jogo.setValor(jogoDTO.getValor());
        jogo.setQuantidadeEmEstoque(jogoDTO.getQuantidadeEmEstoque());
    }

    public PagedModel<EntityModel<JogoDTO>> retornarTodosOsJogos(Pageable pageable){
        Page<Jogo> paginaDeJogos = repository.findAll(pageable);

        if (paginaDeJogos.isEmpty()){
            throw new JogoNaoExisteException("Jogos ainda não foram cadastrados!");
        }

        Page<JogoDTO> paginaDeJogosDto = paginaDeJogos.map(p -> Mapeador.converteObjeto(p, JogoDTO.class));

        paginaDeJogosDto.forEach(this::adicionaMetodoRetornaJogoPeloId);

        Link link = linkTo(methodOn(JogoController.class)
                .retornaTodosOsJogos(pageable.getPageNumber(), pageable.getPageSize(),"asc")).withSelfRel();
        return assembler.toModel(paginaDeJogosDto,link);
    }

    public PagedModel<EntityModel<JogoDTO>> retornaJogosPeloNome(String nome, Pageable pageable){
        Page<Jogo> paginaDeJogos = repository.findByNomeContainingIgnoreCase(nome, pageable);

        if (paginaDeJogos.isEmpty()){
            throw new JogoNaoExisteException("Não existe nenhum jogo com esse nome!");
        }

        Page<JogoDTO> paginaDeJogosDto = paginaDeJogos.map(jogo -> Mapeador.converteObjeto(jogo, JogoDTO.class));
        paginaDeJogosDto.forEach(this::adicionaMetodoRetornaJogoPeloId);

        Link link = linkTo(methodOn(JogoController.class)
                .retornaJogosPeloNome(nome,pageable.getPageNumber(),pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(paginaDeJogosDto, link);
    }

    private void adicionaMetodoRetornaJogoPeloId(JogoDTO jogoDTO){
        jogoDTO.add(
                linkTo(methodOn(JogoController.class)
                        .retornaJogoPeloId(jogoDTO.getId()))
                        .withSelfRel());
    }


    @Transactional
    public JogoDTO desativaJogo(Long id){
        Optional<Jogo> jogoRetornado = repository.findById(id);

        if (jogoRetornado.isEmpty()){
            throw new JogoNaoExisteException("Jogo não existe");
        }

        repository.desativaJogo(id);

        return Mapeador.converteObjeto(jogoRetornado.get(), JogoDTO.class);
    }

}
