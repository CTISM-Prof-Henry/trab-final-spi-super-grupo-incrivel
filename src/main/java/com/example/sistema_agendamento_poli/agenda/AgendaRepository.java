package com.example.sistema_agendamento_poli.agenda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate; // NOVO IMPORT: Substitui java.sql.Date
import java.time.LocalTime; // NOVO IMPORT: Substitui java.sql.Time
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long>, JpaSpecificationExecutor<Agenda> {

    // Calendario diario da sala
    // Retorna agendas de uma sala em um dia, ordenadas por horario inicial
    List<Agenda> findBySalaIdAndDataOrderByHorarioInicio(Long salaId, LocalDate data); // Tipo alterado

    // sobreposicao (status "Ocupado")
    // Verifica se ha conflito de horario na sala para a data informada
    boolean existsBySalaIdAndDataAndStatusAndHorarioInicioLessThanAndHorarioFimGreaterThan(
            Long salaId,
            LocalDate data, // Tipo alterado
            String status,
            LocalTime fim, // Tipo alterado
            LocalTime inicio // Tipo alterado
    );

    // Minhas reservas
    // Lista agendas do usuario em ordem decrescente de data/horario
    Page<Agenda> findByUsuarioIdOrderByDataDescHorarioInicioDesc(Long usuarioId, Pageable pageable);

    // Visao semanal/mensal
    // Busca agendas de uma sala em um intervalo de datas, ordenadas por dia/hora
    List<Agenda> findBySalaIdAndDataBetweenOrderByDataAscHorarioInicioAsc(
            Long salaId,
            LocalDate dataInicio, // Tipo alterado
            LocalDate dataFim // Tipo alterado
    );
}