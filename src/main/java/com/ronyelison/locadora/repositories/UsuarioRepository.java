package com.ronyelison.locadora.repositories;

import com.ronyelison.locadora.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByEmail(String email);
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
