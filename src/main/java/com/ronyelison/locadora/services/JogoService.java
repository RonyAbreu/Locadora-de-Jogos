package com.ronyelison.locadora.services;

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

@Service
public class JogoService {

    private JogoRepository repository;

    @Autowired
    public JogoService(JogoRepository repository) {
        this.repository = repository;
    }

    public JogoDTO adicionarJogo(JogoDTO jogoDTO){
        Optional<Jogo> jogoRetornado = repository.findByNome(jogoDTO.getNome());

        if (jogoRetornado.isPresent()){
            throw new JogoJaExisteException("Jogo já existe!");
        }

        Jogo jogoParaSalvar = Mapeador.converteObjeto(jogoDTO, Jogo.class);
        repository.save(jogoParaSalvar);

        return Mapeador.converteObjeto(jogoParaSalvar,JogoDTO.class);
    }

    public Jogo retornaJogoPeloId(Long id){
        Optional<Jogo> jogoRetornado = repository.findById(id);

        if (jogoRetornado.isEmpty()){
            throw new JogoNaoExisteException("Jogo não existe");
        }

        return jogoRetornado.get();
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

        return Mapeador.converteObjeto(jogoRetornado.get(),JogoDTO.class);
    }

    private void atualizarDados(Jogo jogo, JogoDTO jogoDTO) {
        jogo.setNome(jogoDTO.getNome());
        jogo.setGenero(jogoDTO.getGenero());
        jogo.setValor(jogoDTO.getValor());
        jogo.setQuantidadeEmEstoque(jogoDTO.getQuantidadeEmEstoque());
    }

    public List<Jogo> retornarTodosOsJogos(){
        List<Jogo> listaDeJogos = repository.findAll();

        if (listaDeJogos.isEmpty()){
            throw new JogoNaoExisteException("Lista vazia");
        }

        return listaDeJogos;
    }

    public Jogo retornaJogoPeloNome(String nome){
        Optional<Jogo> jogoRetornado = repository.findByNome(nome);

        if (jogoRetornado.isEmpty()){
            throw new JogoNaoExisteException("Jogo não existe");
        }

        return jogoRetornado.get();
    }
}
