package com.ronyelison.locadora.repositories;

import com.ronyelison.locadora.entities.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JogoRepository extends JpaRepository<Jogo,Long> {
    Optional<Jogo> findByNome(String nome);
    List<Jogo> findByNomeContainingIgnoreCase(String nome);
}
