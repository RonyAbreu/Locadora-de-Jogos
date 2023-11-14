package com.ronyelison.locadora.services;

import com.ronyelison.locadora.controllers.JogoController;
import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.entities.Jogo;
import com.ronyelison.locadora.mapper.Mapeador;
import com.ronyelison.locadora.repositories.JogoRepository;
import com.ronyelison.locadora.services.exceptions.JogoJaExisteException;
import com.ronyelison.locadora.services.exceptions.JogoNaoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class JogoService {

    private JogoRepository repository;

    @Autowired
    public JogoService(JogoRepository repository) {
        this.repository = repository;
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

    public List<JogoDTO> retornarTodosOsJogos(){
        List<JogoDTO> listaDeJogos = Mapeador.converteListaDeObjetos(repository.findAll(),JogoDTO.class);

        if (listaDeJogos.isEmpty()){
            throw new JogoNaoExisteException("Lista vazia");
        }

        for (JogoDTO jogoDTO : listaDeJogos){
            adicionaMetodoRetornaJogoPeloId(jogoDTO);
        }

        return listaDeJogos;
    }

    public List<JogoDTO> retornaJogosPeloNome(String nome){
        List<JogoDTO> listaDeJogos = Mapeador
                .converteListaDeObjetos(repository.findByNomeContainingIgnoreCase(nome), JogoDTO.class);

        if (listaDeJogos.isEmpty()){
            throw new JogoNaoExisteException("Lista de jogos está vazia!");
        }

        for (JogoDTO jogoDTO : listaDeJogos){
            adicionaMetodoRetornaJogoPeloId(jogoDTO);
        }

        return listaDeJogos;
    }

    private void adicionaMetodoRetornaJogoPeloId(JogoDTO jogoDTO){
        jogoDTO.add(
                linkTo(methodOn(JogoController.class)
                        .retornaJogoPeloId(jogoDTO.getId()))
                        .withSelfRel());
    }
}
