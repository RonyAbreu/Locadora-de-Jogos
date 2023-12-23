package com.ronyelison.locadora.controllers.exceptions;

import com.ronyelison.locadora.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ErroControllerHandler {

    @ExceptionHandler(JogoJaExisteException.class)
    public ResponseEntity<RespostaDeErro> jogoJaExisteErro(JogoJaExisteException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        RespostaDeErro respostaDeErro = new RespostaDeErro(Instant.now(),status.value(),exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(respostaDeErro);
    }

    @ExceptionHandler(JogoNaoExisteException.class)
    public ResponseEntity<RespostaDeErro> jogoNaoExisteErro(JogoNaoExisteException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        RespostaDeErro respostaDeErro = new RespostaDeErro(Instant.now(),status.value(),exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(respostaDeErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaDeErro> campoInvalidoErro(MethodArgumentNotValidException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        RespostaDeValidacao respostaDeValidacao = new RespostaDeValidacao(Instant.now(),status.value(),"Erro de validação", request.getRequestURI());

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            respostaDeValidacao.adicionaErroNaLista(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(respostaDeValidacao);
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<RespostaDeErro> usuarioJaExisteErro(UsuarioJaExisteException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        RespostaDeErro respostaDeErro = new RespostaDeErro(Instant.now(),status.value(),exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(respostaDeErro);
    }

    @ExceptionHandler(UsuarioNaoExisteException.class)
    public ResponseEntity<RespostaDeErro> usuarioNaoExisteErro(UsuarioNaoExisteException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        RespostaDeErro respostaDeErro = new RespostaDeErro(Instant.now(),status.value(),exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(respostaDeErro);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<RespostaDeErro> tokenInvalidoErro(TokenException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN;
        RespostaDeErro respostaDeErro = new RespostaDeErro(Instant.now(),status.value(),"Token inválido",request.getRequestURI());
        return ResponseEntity.status(status).body(respostaDeErro);
    }
}
