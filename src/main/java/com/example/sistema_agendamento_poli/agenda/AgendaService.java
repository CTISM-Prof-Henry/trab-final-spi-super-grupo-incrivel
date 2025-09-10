package com.example.sistema_agendamento_poli.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    @Autowired
    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Agenda criarAgenda(Agenda agenda) {
        boolean conflito = agendaRepository.existsBySalaIdAndDataAndStatusAndHorarioInicioLessThanAndHorarioFimGreaterThan(

                agenda.getSala().getId(),
                agenda.getData(),
                "Ocupado",
                agenda.getHorarioFim(),
                agenda.getHorarioInicio()
        );

        if (conflito) {
            throw new RuntimeException("Conflito de horário para esta sala na data informada");
        }

        return agendaRepository.save(agenda);
    }

    public List<Agenda> listarAgendas() {
        return agendaRepository.findAll();
    }

    public Optional<Agenda> buscarPorId(Long id) {
        return agendaRepository.findById(id);
    }

    public List<Agenda> calendarioDiarioSala(Long salaId, Date data) {
        return agendaRepository.findBySalaIdAndDataOrderByHorarioInicio(salaId, data);
    }

    public Page<Agenda> minhasReservas(Long usuarioId, Pageable pageable) {
        return agendaRepository.findByUsuarioIdOrderByDataDescHorarioInicioDesc(usuarioId, pageable);
    }

    public List<Agenda> visaoIntervalo(Long salaId, Date dataInicio, Date dataFim) {
        return agendaRepository.findBySalaIdAndDataBetweenOrderByDataAscHorarioInicioAsc(salaId, dataInicio, dataFim);
    }

    public Agenda atualizarAgenda(Long id, Agenda agendaAtualizada) {
        return agendaRepository.findById(id).map(agenda -> {

            agenda.setSala(agendaAtualizada.getSala());
            agenda.setUsuario(agendaAtualizada.getUsuario());
            agenda.setData(agendaAtualizada.getData());
            agenda.setHorarioInicio(agendaAtualizada.getHorarioInicio());
            agenda.setHorarioFim(agendaAtualizada.getHorarioFim());
            agenda.setStatus(agendaAtualizada.getStatus());
            return agendaRepository.save(agenda);
        }).orElseThrow(() -> new RuntimeException("Agenda não encontrada com o id: " + id));
    }

    public void deletarAgenda(Long id) {

        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Agenda não encontrada com o id: " + id);
        }
    }
}
