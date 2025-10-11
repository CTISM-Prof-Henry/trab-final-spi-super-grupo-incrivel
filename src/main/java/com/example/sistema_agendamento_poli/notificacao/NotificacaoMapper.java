package com.example.sistema_agendamento_poli.notificacao;

import com.example.sistema_agendamento_poli.agenda.Agenda;
import com.example.sistema_agendamento_poli.usuario.Usuario;

public final class NotificacaoMapper { // <--- Adicionado 'final'

    /**
     * Construtor privado para evitar a instanciação da classe utilitária.
     */
    private NotificacaoMapper() { // <--- Construtor privado adicionado
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    public static NotificacaoDTO toDTO(Notificacao notificacao) {
        if (notificacao == null) {
            return null;
        }

        return NotificacaoDTO.builder()
                .id(notificacao.getId())
                .mensagem(notificacao.getMensagem())
                .usuarioId(notificacao.getUsuario().getId())
                .agendaId(notificacao.getAgenda().getId())
                .build();
    }

    public static Notificacao toEntity(NotificacaoDTO dto, Usuario usuario, Agenda agenda) {
        if (dto == null || usuario == null || agenda == null) {
            return null;
        }

        Notificacao notificacao = new Notificacao();
        notificacao.setId(dto.getId()); // normalmente null em criação
        notificacao.setMensagem(dto.getMensagem());
        notificacao.setUsuario(usuario);
        notificacao.setAgenda(agenda);

        return notificacao;
    }
}