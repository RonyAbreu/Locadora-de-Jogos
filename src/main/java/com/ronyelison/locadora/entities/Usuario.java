package com.ronyelison.locadora.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Objects;

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
    @Column(unique = true)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (cargo == Cargo.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("COMUM"));
        else return List.of(new SimpleGrantedAuthority("COMUM"));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return senha;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return contaNaoExpirada;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return contaNaoBloqueada;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return credenciaisNaoExpiradas;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
