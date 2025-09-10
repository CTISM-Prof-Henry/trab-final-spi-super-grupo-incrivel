package com.example.sistema_agendamento_poli.sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    // Busca sala por codigo
    // Retorna a sala com o codigo informado
    Optional<Sala> findByCodigo(String codigo);

    // Lista salas por bloco
    // Retorna salas de um bloco ordenadas por codigo
    List<Sala> findByBlocoIdOrderByCodigoAsc(Long blocoId);

    // Lista salas por bloco e andar
    // Retorna salas de um bloco e andar ordenadas por codigo
    List<Sala> findByBlocoIdAndAndarOrderByCodigoAsc(Long blocoId, Integer andar);

    // Conta salas do bloco
    // Retorna a quantidade de salas vinculadas ao bloco
    long countByBlocoId(Long blocoId);

    // Lista todas ordenadas por codigo
    // Retorna todas as salas em ordem alfabetica por codigo
    List<Sala> findAllByOrderByCodigoAsc();
}
