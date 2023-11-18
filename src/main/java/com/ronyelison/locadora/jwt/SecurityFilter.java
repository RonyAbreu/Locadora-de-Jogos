package com.ronyelison.locadora.jwt;

import com.ronyelison.locadora.entities.Usuario;
import com.ronyelison.locadora.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    public SecurityFilter(TokenService tokenService, UsuarioDetailsService usuarioDetailsService) {
        this.tokenService = tokenService;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recuperaToken(request);

        if (token != null){
            var emailDoUsuario = tokenService.validaTokenPeloSujeito(token);
            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(emailDoUsuario);

            var usernamePassword = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePassword);
        }

        filterChain.doFilter(request,response);
    }

    private String recuperaToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")){
            return token.substring("Bearer ".length());
        }
        return null;
    }
}
