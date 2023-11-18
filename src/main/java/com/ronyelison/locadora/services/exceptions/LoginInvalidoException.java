package com.ronyelison.locadora.services.exceptions;

public class LoginInvalidoException extends RuntimeException{
    public LoginInvalidoException(String mensagem){
        super(mensagem);
    }
}
