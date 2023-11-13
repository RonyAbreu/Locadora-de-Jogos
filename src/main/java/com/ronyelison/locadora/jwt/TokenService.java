package com.ronyelison.locadora.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ronyelison.locadora.dto.usuario.UsuarioDeLogin;
import com.ronyelison.locadora.services.exceptions.TokenException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {
    @Value("${security.jwt.secret-key:secret}")
    private String secretKey;
    private Algorithm algorithm = null;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public String gerarToken(UsuarioDeLogin usuarioDeLogin){
        try{
            return JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(tempoDoToken())
                    .withSubject(usuarioDeLogin.getEmail())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new TokenException("Erro na criação do token");
        }
    }

    private Date tempoDoToken() {
        long umaHoraEmSegundos = 3600;
        return Date.from(Instant.now().plusSeconds(umaHoraEmSegundos));
    }

    public String validaTokenPeloSujeito(String token){
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            throw new TokenException("Token inválido ou expirado");
        }
    }
}
