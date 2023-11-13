package com.ronyelison.locadora.services;

import com.ronyelison.locadora.dto.usuario.UsuarioAtualizado;
import com.ronyelison.locadora.dto.usuario.UsuarioDTO;
import com.ronyelison.locadora.entities.Usuario;
import com.ronyelison.locadora.mapper.Mapeador;
import com.ronyelison.locadora.repositories.UsuarioRepository;
import com.ronyelison.locadora.services.exceptions.UsuarioNaoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioAutenticadoService {
    private UsuarioRepository repository;

    @Autowired
    public UsuarioAutenticadoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void removeUsuarioPeloId(Long id){
        Optional<Usuario> usuarioASerDeletado = repository.findById(id);

        if (usuarioASerDeletado.isEmpty()){
            throw new UsuarioNaoExisteException("Usuário não encontrado!");
        }

        repository.delete(usuarioASerDeletado.get());
    }

    public Usuario retornaUsuarioPeloId(Long id){
        Optional<Usuario> usuarioRetornado = repository.findById(id);

        if (usuarioRetornado.isEmpty()){
            throw new UsuarioNaoExisteException("Usuário não encontrado!");
        }
        return usuarioRetornado.get();
    }

    public UsuarioDTO atualizaUsuario(Long id, UsuarioAtualizado usuarioAtualizado){
        Optional<Usuario> usuarioRetornado = repository.findById(id);

        if (usuarioRetornado.isEmpty()){
            throw new UsuarioNaoExisteException("Usuário não encontrado!");
        }

        atualizaDados(usuarioRetornado.get(),usuarioAtualizado);
        repository.save(usuarioRetornado.get());

        return Mapeador.converteObjeto(usuarioRetornado.get(),UsuarioDTO.class);
    }

    private void atualizaDados(Usuario usuario, UsuarioAtualizado usuarioAtualizado) {
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setSenha(usuarioAtualizado.getSenha());
    }
}
