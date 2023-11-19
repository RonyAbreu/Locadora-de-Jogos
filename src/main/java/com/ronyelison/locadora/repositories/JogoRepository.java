package com.ronyelison.locadora.repositories;

import com.ronyelison.locadora.entities.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JogoRepository extends JpaRepository<Jogo,Long> {
    Optional<Jogo> findByNome(String nome);
    List<Jogo> findByNomeContainingIgnoreCase(String nome);
    @Modifying
    @Query("UPDATE Jogo j SET j.ativo = false WHERE j.id=:id")
    void desativaJogo(@Param("id") Long id);
}
