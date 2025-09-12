package com.example.sistema_agendamento_poli.agenda;

import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
public class AgendaController {
    private AgendaService agendaService;
    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;}

    @GetMapping("/listar")
    public List<Agenda> listar() {
        return this.agendaService.listarAgendas();
    }

    @GetMapping("/{id}")
    public Optional<Agenda> buscar(@PathVariable Long id) {
        return this.agendaService.buscarPorId(id);
    }

    @PostMapping("/print-json")
    public void printJson(@RequestBody String json) {
        System.out.println(json);
    }

    @PostMapping()
    public void criarSala(@RequestBody Agenda agenda) {
        this.agendaService.criarAgenda(agenda);
    }

    @PutMapping
    public void atualizar(@RequestBody Agenda agenda, @PathVariable Long id) {
        this.agendaService.atualizarAgenda(id, agenda);
    }

    @DeleteMapping
    public void deletar(@PathVariable Long id){
        this.agendaService.deletarAgenda(id);
    }


    // desenvolver funçao controller para calendarioDiarioSala, minhasReservas, visaoIntervalo

}
