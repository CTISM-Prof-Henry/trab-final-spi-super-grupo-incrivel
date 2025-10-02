package com.example.sistema_agendamento_poli.agenda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping("/listar")
    public List<Agenda> listar() {
        return this.agendaService.listarAgendas();
    }

    @GetMapping("/{id}")
    public Optional<Agenda> buscar(@PathVariable Long id) {
        return this.agendaService.buscarPorId(id);
    }

    @PostMapping()
    public Agenda criarAgenda(@RequestBody Agenda agenda) {
        return this.agendaService.criarAgenda(agenda);
    }

    @PutMapping("/{id}")
    public Agenda atualizar(@RequestBody Agenda agenda, @PathVariable Long id) {
        return this.agendaService.atualizarAgenda(id, agenda);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        this.agendaService.deletarAgenda(id);
    }

    // retorna a agenda diaria de uma sala especifica em uma data
    @GetMapping("/calendario/{salaId}/{data}")
    public List<Agenda> calendarioDiarioSala(@PathVariable Long salaId, @PathVariable Date data) {
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
            @PathVariable Date dataInicio,
            @PathVariable Date dataFim) {
        return this.agendaService.visaoIntervalo(salaId, dataInicio, dataFim);
    }
}
