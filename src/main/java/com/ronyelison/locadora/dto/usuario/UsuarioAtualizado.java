package com.ronyelison.locadora.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioAtualizado {

    @NotBlank(message = "Preencha todos os campos")
    @Size(min = 3, max = 60, message = "Número de caracteres do Nome é inválido")
    private String nome;

    @NotBlank(message = "Preencha todos os campos")
    @Size(min = 8 , message = "Número de caracteres da senha é inválido")
    private String senha;
}
