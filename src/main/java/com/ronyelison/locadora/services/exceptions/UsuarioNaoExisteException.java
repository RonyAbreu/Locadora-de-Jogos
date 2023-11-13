package com.ronyelison.locadora.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioNaoExisteException extends RuntimeException{
    public UsuarioNaoExisteException(String mensagem){
        super(mensagem);
    }
}
