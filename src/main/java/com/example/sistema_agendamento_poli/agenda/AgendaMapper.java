package com.example.sistema_agendamento_poli.agenda;

import com.example.sistema_agendamento_poli.sala.Sala;
import com.example.sistema_agendamento_poli.usuario.Usuario;

public final class AgendaMapper {

    /**
     * Construtor privado para evitar a instanciação da classe utilitária.
     */
    private AgendaMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    public static AgendaDTO toDTO(Agenda agenda) {
        if (agenda == null) {
            return null;
        }

        return AgendaDTO.builder()
                .id(agenda.getId())
                .salaId(agenda.getSala().getId())
                .usuarioId(agenda.getUsuario().getId())
                .data(agenda.getData())
                .horarioInicio(agenda.getHorarioInicio())
                .horarioFim(agenda.getHorarioFim())
                .status(agenda.getStatus())
                .build();
    }

    public static Agenda toEntity(AgendaDTO dto, Sala sala, Usuario usuario) {
        if (dto == null || sala == null || usuario == null) {
            return null;
        }

        Agenda agenda = new Agenda();
        agenda.setId(dto.getId());
        agenda.setSala(sala);
        agenda.setUsuario(usuario);
        agenda.setData(dto.getData());
        agenda.setHorarioInicio(dto.getHorarioInicio());
        agenda.setHorarioFim(dto.getHorarioFim());
        agenda.setStatus(dto.getStatus());

        return agenda;
    }
}