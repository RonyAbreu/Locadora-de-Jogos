package com.ronyelison.locadora.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDeRegistroDTO {
    private String nome;
    private String email;
    private String senha;
}
