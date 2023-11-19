package com.ronyelison.locadora.integration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement
public class UsuarioDeLoginTest {

    @NotBlank(message = "Preencha todos os campos")
    @Size(max = 50 , message = "Número de caracteres do email é inválido")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Preencha todos os campos")
    @Size(min = 8 , message = "Número de caracteres da senha é inválido")
    private String senha;
}
