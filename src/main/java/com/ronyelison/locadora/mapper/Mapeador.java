package com.ronyelison.locadora.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapeador {

    private static final ModelMapper mapper = new ModelMapper();

    public static <O,D> D converteObjeto(O origem, Class<D> destino){
        return mapper.map(origem,destino);
    }

    public static <O,D> List<D> converteListaDeObjetos(List<O> listaDeOrigem, Class<D> destino){
        List<D> listaDeDestino = new ArrayList<>();
        for (O origem : listaDeOrigem){
            listaDeDestino.add(mapper.map(origem,destino));
        }
        return listaDeDestino;
    }
}
