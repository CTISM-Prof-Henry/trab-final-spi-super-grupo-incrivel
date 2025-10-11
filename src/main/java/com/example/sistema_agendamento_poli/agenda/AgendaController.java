package com.example.sistema_agendamento_poli.agenda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// Imports do Spring Web otimizados
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate; // Usado para os path variables de data
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    // --- MÉTODOS DE CONSULTA (Podem retornar Entidade ou DTO) ---

    @GetMapping("/listar")
    public List<Agenda> listar() {
        return this.agendaService.listarAgendas();
    }

    @GetMapping("busca/id/{id}")
    public Optional<Agenda> buscar(@PathVariable Long id) {
        return this.agendaService.buscarPorId(id);
    }

    // --- CRIAÇÃO (USANDO DTO) ---

    @PostMapping()
    public Agenda criarAgenda(@RequestBody AgendaDTO agendaDTO) { // Usando AgendaDTO
        return this.agendaService.criarAgenda(agendaDTO); // Service deve aceitar DTO
    }

    // --- ATUALIZAÇÃO (USANDO DTO) ---

    @PutMapping("/{id}")
    public Agenda atualizar(@RequestBody AgendaDTO agendaDTO, @PathVariable Long id) { // Usando AgendaDTO
        return this.agendaService.atualizarAgenda(id, agendaDTO); // Service deve aceitar DTO
    }

    // --- DELEÇÃO ---

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        this.agendaService.deletarAgenda(id);
    }

    // --- BUSCAS POR DATA (USANDO LocalDate) ---

    // retorna a agenda diaria de uma sala especifica em uma data
    @GetMapping("/calendario/{salaId}/{data}")
    public List<Agenda> calendarioDiarioSala(@PathVariable Long salaId, @PathVariable LocalDate data) {
        return this.agendaService.calendarioDiarioSala(salaId, data);
    }

    // retorna as reservas feitas por um usuario especifico
    @GetMapping("/minhas-reservas/{usuarioId}")
    public Page<Agenda> minhasReservas(@PathVariable Long usuarioId, Pageable pageable) {
        return this.agendaService.minhasReservas(usuarioId, pageable);
    }

    // retorna a agenda de uma sala dentro de um intervalo de datas
    @GetMapping("/visao-intervalo/{salaId}/{dataInicio}/{dataFim}")
    public List<Agenda> visaoIntervalo(
            @PathVariable Long salaId,
            @PathVariable LocalDate dataInicio, // Usando LocalDate
            @PathVariable LocalDate dataFim) { // Usando LocalDate
        return this.agendaService.visaoIntervalo(salaId, dataInicio, dataFim);
    }
}