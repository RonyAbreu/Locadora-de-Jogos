package com.ronyelison.locadora.services.exceptions;

public class TokenException extends RuntimeException{
    public TokenException(String mensagem){
        super(mensagem);
    }
}
