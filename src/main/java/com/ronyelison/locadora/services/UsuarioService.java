package com.ronyelison.locadora.services;

import com.ronyelison.locadora.dto.usuario.TokenDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDeLogin;
import com.ronyelison.locadora.dto.usuario.UsuarioDeRegistroDTO;
import com.ronyelison.locadora.entities.Usuario;
import com.ronyelison.locadora.jwt.TokenService;
import com.ronyelison.locadora.mapper.Mapeador;
import com.ronyelison.locadora.repositories.UsuarioRepository;
import com.ronyelison.locadora.services.exceptions.UsuarioJaExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public UsuarioDTO cadastraUsuario(UsuarioDeRegistroDTO usuarioDeRegistro){
        Usuario usuarioRetornado = (Usuario) usuarioRepository.findByEmail(usuarioDeRegistro.getEmail());
        if (usuarioRetornado != null){
            throw new UsuarioJaExisteException("Esse usuário já existe!");
        }

        Usuario usuarioParaSalvar = Mapeador.converteObjeto(usuarioDeRegistro, Usuario.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var senhaEncriptada = passwordEncoder.encode(usuarioParaSalvar.getSenha());
        usuarioParaSalvar.setSenha(senhaEncriptada);
        usuarioRepository.save(usuarioParaSalvar);

        return Mapeador.converteObjeto(usuarioParaSalvar,UsuarioDTO.class);
    }

    public TokenDTO login(UsuarioDeLogin usuarioDeLogin) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(usuarioDeLogin.getEmail(), usuarioDeLogin.getSenha());
        authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken(usuarioDeLogin);
        return new TokenDTO(token);
    }
}
