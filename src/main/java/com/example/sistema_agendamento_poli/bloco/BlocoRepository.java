package com.example.sistema_agendamento_poli.bloco;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlocoRepository extends JpaRepository<Bloco, Long> {

    // Busca bloco por nome
    // Retorna o bloco com o nome informado
    Optional<Bloco> findByNome(String nome);

    // Verifica existencia por nome
    // Retorna true se existe bloco com esse nome
    boolean existsByNome(String nome);

    // Lista blocos ordenados por nome
    // Retorna todos os blocos em ordem alfabetica por nome
    List<Bloco> findAllByOrderByNomeAsc();
}
