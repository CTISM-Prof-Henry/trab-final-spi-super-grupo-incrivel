package com.example.sistema_agendamento_poli.bloco;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blocos")
public class BlocoController {
    private BlocoService blocoService;
    public BlocoController(BlocoService blocoService) {
        this.blocoService = blocoService;}

    @GetMapping("/listar")
    public List<Bloco> listar() {
        return this.blocoService.listarBlocos();
    }

    @GetMapping("busca/id/{id}")
    public Optional<Bloco> buscar(@PathVariable Long id) {
        return this.blocoService.buscarPorId(id);
    }

    @PostMapping()
    public void criarBLoco(@RequestBody Bloco bloco) {
        this.blocoService.criarBloco(bloco);
    }

    @PostMapping("/print-json")
    public void printJson(@RequestBody String json) {
        System.out.println(json);
    }

    @PutMapping("/{id}")
    public void atualizar(@RequestBody Bloco bloco, @PathVariable Long id) {
        this.blocoService.atualizarBloco(id, bloco);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        this.blocoService.deletarBloco(id);
    }
}
