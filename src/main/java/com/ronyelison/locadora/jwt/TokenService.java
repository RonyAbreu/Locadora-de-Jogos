package com.ronyelison.locadora.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ronyelison.locadora.dto.usuario.TokenDTO;
import com.ronyelison.locadora.dto.usuario.UsuarioDeLogin;
import com.ronyelison.locadora.services.exceptions.TokenException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {
    @Value("${security.jwt.secret-key:secret}")
    private String secretKey;
    private Algorithm algorithm = null;
    private final Long TRES_HORAS_EM_SEGUNDOS = 10800L;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO criarToken(UsuarioDeLogin usuarioDeLogin){
        Instant criadoEm = LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")).minusSeconds(TRES_HORAS_EM_SEGUNDOS);
        Instant validoAte = tempoDoToken();

        var token = gerarToken(usuarioDeLogin,criadoEm,validoAte);
        var refreshToken = gerarRefreshToken(usuarioDeLogin,criadoEm);

        return new TokenDTO(token,refreshToken,criadoEm,validoAte,true);
    }

    public String gerarToken(UsuarioDeLogin usuarioDeLogin, Instant criadoEm, Instant validoAte){
        try{
            return JWT.create()
                    .withIssuedAt(criadoEm)
                    .withExpiresAt(validoAte)
                    .withSubject(usuarioDeLogin.getEmail())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new TokenException("Erro na criação do token");
        }
    }

    public String gerarRefreshToken(UsuarioDeLogin usuarioDeLogin, Instant criadoEm){
        try{
            return JWT.create()
                    .withIssuedAt(criadoEm)
                    .withExpiresAt(tempoDoRefreshToken())
                    .withSubject(usuarioDeLogin.getEmail())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new TokenException("Erro na criação do token");
        }
    }

    private Instant tempoDoToken() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00")).minusSeconds(TRES_HORAS_EM_SEGUNDOS);
    }

    private Instant tempoDoRefreshToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")).minusSeconds(TRES_HORAS_EM_SEGUNDOS);
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
