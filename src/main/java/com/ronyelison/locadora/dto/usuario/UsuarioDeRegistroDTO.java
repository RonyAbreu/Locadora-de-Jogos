package com.ronyelison.locadora.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDeRegistroDTO {
    @NotBlank(message = "Preencha todos os campos")
    @Size(min = 3, max = 60, message = "Número de caracteres do Nome é inválido")
    private String nome;

    @NotBlank(message = "Preencha todos os campos")
    @Size(max = 50 , message = "Número de caracteres do email é inválido")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Preencha todos os campos")
    @Size(min = 8 , message = "Número de caracteres da senha é inválido")
    private String senha;
}
