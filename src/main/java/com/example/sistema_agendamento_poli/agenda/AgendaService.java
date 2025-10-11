package com.example.sistema_agendamento_poli.agenda;

import com.example.sistema_agendamento_poli.sala.Sala;
import com.example.sistema_agendamento_poli.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate; // Novo import para a data moderna
import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    @Autowired
    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    // --- MÉTODOS DE CONVERSÃO (Exemplo de lógica de mapeamento) ---

    // Este método converte o DTO de entrada para a Entidade que será salva
    private Agenda toEntity(AgendaDTO dto) {
        Agenda agenda = new Agenda();

        // Simplesmente cria referências para FKs (o repositório deve buscar a entidade completa)
        agenda.setSala(new Sala(dto.getSalaId()));
        agenda.setUsuario(new Usuario(dto.getUsuarioId()));

        // Mapeamento dos campos
        agenda.setData(dto.getData());
        agenda.setHorarioInicio(dto.getHorarioInicio());
        agenda.setHorarioFim(dto.getHorarioFim());
        agenda.setStatus(dto.getStatus());

        return agenda;
    }

    // --- CRIAÇÃO (Agora aceita DTO) ---

    public Agenda criarAgenda(AgendaDTO agendaDTO) {
        Agenda novaAgenda = toEntity(agendaDTO);

        // Os getters da entidade Agenda agora usam LocalDate e LocalTime
        boolean conflito = agendaRepository.existsBySalaIdAndDataAndStatusAndHorarioInicioLessThanAndHorarioFimGreaterThan(
                novaAgenda.getSala().getId(),
                novaAgenda.getData(),
                "Ocupado",
                novaAgenda.getHorarioFim(),
                novaAgenda.getHorarioInicio()
        );

        if (conflito) {
            throw new RuntimeException("Conflito de horário para esta sala na data informada");
        }

        return agendaRepository.save(novaAgenda);
    }

    // --- ATUALIZAÇÃO (Agora aceita DTO) ---

    public Agenda atualizarAgenda(Long id, AgendaDTO agendaDTO) {
        return agendaRepository.findById(id).map(agenda -> {

            // As FKS devem ser tratadas de forma mais robusta (buscando as entidades Sala/Usuario)
            // Aqui, apenas atualizamos os campos do DTO

            agenda.setData(agendaDTO.getData());
            agenda.setHorarioInicio(agendaDTO.getHorarioInicio());
            agenda.setHorarioFim(agendaDTO.getHorarioFim());
            agenda.setStatus(agendaDTO.getStatus());

            return agendaRepository.save(agenda);
        }).orElseThrow(() -> new RuntimeException("Agenda não encontrada com o id: " + id));
    }

    // --- BUSCAS (Usando LocalDate) ---

    public List<Agenda> calendarioDiarioSala(Long salaId, LocalDate data) { // Tipo alterado
        return agendaRepository.findBySalaIdAndDataOrderByHorarioInicio(salaId, data);
    }

    public List<Agenda> visaoIntervalo(Long salaId, LocalDate dataInicio, LocalDate dataFim) { // Tipos alterados
        return agendaRepository.findBySalaIdAndDataBetweenOrderByDataAscHorarioInicioAsc(salaId, dataInicio, dataFim);
    }

    // --- MÉTODOS INALTERADOS ---

    public List<Agenda> listarAgendas() {
        return agendaRepository.findAll();
    }

    public Optional<Agenda> buscarPorId(Long id) {
        return agendaRepository.findById(id);
    }

    public Page<Agenda> minhasReservas(Long usuarioId, Pageable pageable) {
        return agendaRepository.findByUsuarioIdOrderByDataDescHorarioInicioDesc(usuarioId, pageable);
    }

    public void deletarAgenda(Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Agenda não encontrada com o id: " + id);
        }
    }
}