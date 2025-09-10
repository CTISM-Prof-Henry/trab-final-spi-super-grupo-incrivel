package com.example.sistema_agendamento_poli.notificacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    // Lista notificacoes do usuario
    // Retorna notificacoes do usuario em ordem decrescente de id (mais recentes primeiro) paginadas
    Page<Notificacao> findByUsuarioIdOrderByIdDesc(Long usuarioId, Pageable pageable);

    // Lista por agenda
    // Retorna notificacoes ligadas a uma agenda especifica
    List<Notificacao> findByAgendaId(Long agendaId);

    // Remove por usuario
    // Exclui todas as notificacoes de um usuario
    void deleteByUsuarioId(Long usuarioId);
}
