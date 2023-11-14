package com.ronyelison.locadora.entities;

import com.ronyelison.locadora.entities.enums.Cargo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Cargo cargo = Cargo.COMUM;
    private boolean contaNaoExpirada = true;
    private boolean contaNaoBloqueada = true;
    private boolean credenciaisNaoExpiradas = true;
    private boolean ativo = true;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (cargo == Cargo.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("COMUM"));
        else return List.of(new SimpleGrantedAuthority("COMUM"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return contaNaoExpirada;
    }

    @Override
    public boolean isAccountNonLocked() {
        return contaNaoBloqueada;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credenciaisNaoExpiradas;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
