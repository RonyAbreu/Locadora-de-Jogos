package com.ronyelison.locadora.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "tb_jogo")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private String genero;
    private Double valor;
    private Integer quantidadeEmEstoque;
    private String urlDaImagem;

    public Jogo(String nome, String genero, Double valor, Integer quantidadeEmEstoque, String urlDaImagem) {
        this.nome = nome;
        this.genero = genero;
        this.valor = valor;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.urlDaImagem = urlDaImagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogo jogo = (Jogo) o;
        return Objects.equals(nome, jogo.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
