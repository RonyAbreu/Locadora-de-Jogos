package com.ronyelison.locadora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JogoDTO extends RepresentationModel<JogoDTO> {
    private Long id;

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

    @NotBlank(message = "Preencha todos os campos")
    private String urlDaImagem;
}
