package com.ronyelison.locadora.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class RespostaDeValidacao extends RespostaDeErro{

    private List<CampoEMensagem> listaDeErros = new ArrayList<>();
    public RespostaDeValidacao(Instant erroGeradoEm, Integer status, String mensagem, String url) {
        super(erroGeradoEm, status, mensagem, url);
    }

    public void adicionaErroNaLista(String campo, String mensagem){
        this.listaDeErros.add(new CampoEMensagem(campo,mensagem));
    }
}
