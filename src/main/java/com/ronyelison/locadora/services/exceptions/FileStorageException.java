package com.ronyelison.locadora.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException{
    public FileStorageException(String mensagem){
        super(mensagem);
    }

    public FileStorageException(String mensagem, Throwable cause){
        super(mensagem, cause);
    }
}



