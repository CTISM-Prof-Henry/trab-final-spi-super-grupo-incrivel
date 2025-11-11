package com.example.sistema_agendamento_poli.sala;

// Imports do Spring Web otimizados
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private SalaService salaService;
    public SalaController(SalaService salaService) {
        this.salaService = salaService;}

    // --- MÉTODOS DE CONSULTA (Retornam Entidade) ---

    @GetMapping("/listar")
    public List<Sala> listar() {
        return this.salaService.listarSalas();
    }

    @GetMapping("busca/id/{id}")
    public Optional<Sala> buscar(@PathVariable Long id) {
        return this.salaService.buscarPorId(id);
    }

    @GetMapping("busca/codigo/{codigo}")
    public Optional<Sala> buscarPorCodigo(@PathVariable String codigo) {
        return this.salaService.buscarPorCodigo(codigo);
    }

    // Mantido como está (apenas para debug)
    @PostMapping("/print-json")
    public void printJson(@RequestBody String json) {
        System.out.println(json);
    }

    // --- CRIAÇÃO (USANDO DTO) ---

    @PostMapping()
    public void criarSala(@RequestBody SalaDTO dto) { // Alterado para DTO
        this.salaService.criarSala(dto); // Service já foi atualizado para DTO
    }

    // --- ATUALIZAÇÃO (USANDO DTO) ---

    @PutMapping("/{id}")
    public void atualizar(@RequestBody SalaDTO dto, @PathVariable Long id) { // Alterado para DTO
        this.salaService.atualizarSala(id, dto); // Service já foi atualizado para DTO
    }

    // --- DELEÇÃO ---

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        this.salaService.deletarSala(id);
    }
}