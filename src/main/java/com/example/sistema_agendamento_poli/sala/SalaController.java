package com.example.sistema_agendamento_poli.sala;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private SalaService salaService;
    public SalaController(SalaService salaService) {
        this.salaService = salaService;}

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

    @PostMapping("/print-json")
    public void printJson(@RequestBody String json) {
        System.out.println(json);
    }

    @PostMapping()
    public void criarSala(@RequestBody Sala sala) {
        this.salaService.criarSala(sala);
    }

    @PutMapping("/{id}")
    public void atualizar(@RequestBody Sala sala, @PathVariable Long id) {
        this.salaService.atualizarSala(id, sala);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        this.salaService.deletarSala(id);
    }
}
