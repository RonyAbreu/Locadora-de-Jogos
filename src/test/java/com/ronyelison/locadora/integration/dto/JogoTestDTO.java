package com.ronyelison.locadora.integration.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class JogoTestDTO{
    private Long id;
    private String nome;
    private String genero;
    private Integer quantidadeEmEstoque;
    private Double valor;
    private String urlDaImagem;

    public JogoTestDTO(){

    }

    public JogoTestDTO(String nome, String genero, Integer quantidadeEmEstoque, Double valor, String urlDaImagem) {
        this.nome = nome;
        this.genero = genero;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.valor = valor;
        this.urlDaImagem = urlDaImagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogoTestDTO that = (JogoTestDTO) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}

