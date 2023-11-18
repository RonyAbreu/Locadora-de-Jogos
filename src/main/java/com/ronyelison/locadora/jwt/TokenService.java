package com.ronyelison.locadora.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ronyelison.locadora.dto.usuario.TokenDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDeLogin;
import com.ronyelison.locadora.services.exceptions.TokenException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

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

    public TokenDTO criarToken(String email){
        Instant criadoEm = LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"));
        Instant validoAte = tempoDoToken();

        var token = gerarToken(email,criadoEm,validoAte);
        var refreshToken = gerarRefreshToken(email,criadoEm);

        return new TokenDTO(token,refreshToken,criadoEm,validoAte,true);
    }

    public TokenDTO refreshToken(String refreshToken){
        if (refreshToken.contains("Bearer ")){
            refreshToken = refreshToken.substring("Bearer ".length());
        }

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);

        String email = decodedJWT.getSubject();

        return criarToken(email);
    }

    public String gerarToken(String email, Instant criadoEm, Instant validoAte){
        try{
            return JWT.create()
                    .withIssuedAt(criadoEm)
                    .withExpiresAt(validoAte)
                    .withSubject(email)
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new TokenException("Erro na criação do token");
        }
    }

    public String gerarRefreshToken(String email, Instant criadoEm){
        try{
            return JWT.create()
                    .withIssuedAt(criadoEm)
                    .withExpiresAt(tempoDoRefreshToken())
                    .withSubject(email)
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new TokenException("Erro na criação do token");
        }
    }

    private Instant tempoDoToken() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant tempoDoRefreshToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validaTokenPeloSujeito(String token){
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTDecodeException e){
            throw new TokenException("Token inválido ou expirado!");
        }
    }
}
