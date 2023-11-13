package com.ronyelison.locadora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogoDTO {
    @NotBlank(message = "Preencha todos os campos")
    @Size(min = 2, max = 30, message = "Número de caracteres do nome é inválido")
    private String nome;

    @NotBlank(message = "Preencha todos os campos")
    @Size(min = 3, max = 30, message = "Número de caracteres do gênero é inválido")
    private String genero;

    @NotNull(message = "Preencha todos os campos")
    private Double valor;

    @NotNull(message = "Preencha todos os campos")
    private Integer quantidadeEmEstoque;
}
