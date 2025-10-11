package com.example.sistema_agendamento_poli.bloco;

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

    // USANDO DTO no @RequestBody
    @PostMapping()
    public void criarBLoco(@RequestBody BlocoDTO blocoDTO) { // Alterado para DTO
        this.blocoService.criarBloco(blocoDTO); // Service deve aceitar DTO
    }

    // Mantendo a assinatura original, pois o tipo é String
    @PostMapping("/print-json")
    public void printJson(@RequestBody String json) {
        System.out.println(json);
    }

    // USANDO DTO no @RequestBody
    @PutMapping("/{id}")
    public void atualizar(@RequestBody BlocoDTO blocoDTO, @PathVariable Long id) { // Alterado para DTO
        this.blocoService.atualizarBloco(id, blocoDTO); // Service deve aceitar DTO
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        this.blocoService.deletarBloco(id);
    }
}