package com.ronyelison.locadora.services;

import com.ronyelison.locadora.dto.JogoDTO;
import com.ronyelison.locadora.entities.Jogo;
import com.ronyelison.locadora.mocks.JogoMock;
import com.ronyelison.locadora.repositories.JogoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class JogoServiceTest {

    JogoMock jogoMock;

    @InjectMocks
    JogoService jogoService;

    @Mock
    JogoRepository jogoRepository;

    @BeforeEach
    public void setUp(){
        jogoMock = new JogoMock();
        openMocks(this);
    }

    @Test
    void adicionarJogo() {
        Jogo jogo = jogoMock.mockJogo(1);
        JogoDTO jogoDTO = jogoMock.mockJogoDto(1);

        Mockito.lenient().when(jogoRepository.save(jogo)).thenReturn(jogo);

        var resultado = jogoService.adicionarJogo(jogoDTO);

        assertNotNull(resultado);
        assertNotNull(resultado.getNome());
        assertNotNull(resultado.getGenero());
        assertNotNull(resultado.getValor());
        assertNotNull(resultado.getQuantidadeEmEstoque());

        assertEquals("Jogo 1", resultado.getNome());
        assertEquals("Gênero 1", resultado.getGenero());
        assertEquals(10, resultado.getValor());
        assertEquals(1, resultado.getQuantidadeEmEstoque());
    }

    @Test
    void retornaJogoPeloId() {
    }

    @Test
    void removeJogoPeloId() {
    }

    @Test
    void atualizaJogo() {
    }

    @Test
    void retornarTodosOsJogos() {
    }

    @Test
    void retornaJogoPeloNome() {
    }
}