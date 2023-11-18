package com.ronyelison.locadora.mapper;

import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.entities.Jogo;
import com.ronyelison.locadora.mocks.JogoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperTest {

    JogoMock jogoMock;

    @BeforeEach
    public void setUp(){
        jogoMock = new JogoMock();
    }

    @Test
    public void jogoParaJogoDTO(){
        JogoDTO jogoDTO = Mapeador.converteObjeto(jogoMock.mockJogo(1),JogoDTO.class);
        assertEquals(1, jogoDTO.getId());
        assertEquals("Jogo 1",jogoDTO.getNome());
        assertEquals("Gênero 1", jogoDTO.getGenero());
        assertEquals(10, jogoDTO.getValor());
        assertEquals(1, jogoDTO.getQuantidadeEmEstoque());
        assertEquals("Url 1", jogoDTO.getUrlDaImagem());
    }

    @Test
    public void listaDeJogoParaListaDeJogoDTO(){
        List<JogoDTO> listaDTO = Mapeador.converteListaDeObjetos(jogoMock.mockListaDeJogos(), JogoDTO.class);

        JogoDTO jogoNumero1 = listaDTO.get(1);

        assertEquals(1, jogoNumero1.getId());
        assertEquals("Jogo 1",jogoNumero1.getNome());
        assertEquals("Gênero 1", jogoNumero1.getGenero());
        assertEquals(10, jogoNumero1.getValor());
        assertEquals(1, jogoNumero1.getQuantidadeEmEstoque());
        assertEquals("Url 1", jogoNumero1.getUrlDaImagem());
    }

    @Test
    public void jogoDTOParaJogo(){
        Jogo jogo = Mapeador.converteObjeto(jogoMock.mockJogoDto(1),Jogo.class);
        assertEquals(1, jogo.getId());
        assertEquals("Jogo 1",jogo.getNome());
        assertEquals("Gênero 1", jogo.getGenero());
        assertEquals(10, jogo.getValor());
        assertEquals(1, jogo.getQuantidadeEmEstoque());
        assertEquals("Url 1", jogo.getUrlDaImagem());
    }

    @Test
    public void listaDeJogoDTOParaListaDeJogo(){
        List<Jogo> lista = Mapeador.converteListaDeObjetos(jogoMock.mockListaDeJogosDto(), Jogo.class);

        Jogo jogoNumero1 = lista.get(1);

        assertEquals(1, jogoNumero1.getId());
        assertEquals("Jogo 1",jogoNumero1.getNome());
        assertEquals("Gênero 1", jogoNumero1.getGenero());
        assertEquals(10, jogoNumero1.getValor());
        assertEquals(1, jogoNumero1.getQuantidadeEmEstoque());
        assertEquals("Url 1", jogoNumero1.getUrlDaImagem());
    }
}
