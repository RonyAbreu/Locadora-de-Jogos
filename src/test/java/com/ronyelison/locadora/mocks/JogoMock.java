package com.ronyelison.locadora.mocks;

import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.entities.Jogo;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
public class JogoMock {

    public Jogo mockJogo(Integer numero){
        Jogo jogo = new Jogo();
        jogo.setId(numero.longValue());
        jogo.setNome("Jogo " + numero);
        jogo.setGenero("Gênero " + numero);
        jogo.setValor(10D);
        jogo.setQuantidadeEmEstoque(numero);
        return jogo;
    }

    public JogoDTO mockJogoDto(Integer numero){
        JogoDTO jogoDTO = new JogoDTO();
        jogoDTO.setNome("JogoDTO " + numero);
        jogoDTO.setGenero("Gênero " + numero);
        jogoDTO.setValor(10D);
        jogoDTO.setQuantidadeEmEstoque(numero);
        return jogoDTO;
    }

    public List<Jogo> mockListaDeJogos(){
        List<Jogo> listaDeJogos = new ArrayList<>();
        for (int i =0; i < 10; i++){
            listaDeJogos.add(mockJogo(i));
        }
        return listaDeJogos;
    }

    public List<JogoDTO> mockListaDeJogosDto(){
        List<JogoDTO> listaDeJogosDto = new ArrayList<>();
        for (int i =0; i < 10; i++){
            listaDeJogosDto.add(mockJogoDto(i));
        }
        return listaDeJogosDto;
    }
}
