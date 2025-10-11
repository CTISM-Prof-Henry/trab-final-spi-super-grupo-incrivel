package com.example.sistema_agendamento_poli.notificacao;

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
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    // --- MÉTODOS DE CONSULTA (Retornam a Entidade) ---

    // retorna todas as notificacoes
    @GetMapping("/listar")
    public List<Notificacao> listar() {
        return this.notificacaoService.listarNotificacoes();
    }

    // retorna uma notificacao especifica pelo id
    @GetMapping("busca/id/{id}")
    public Optional<Notificacao> buscarPorId(@PathVariable Long id) {
        return this.notificacaoService.buscarNotificacaoPorId(id);
    }

    // --- CRIAÇÃO (USANDO DTO) ---

    // cria uma nova notificacao
    @PostMapping
    public Notificacao criar(@RequestBody NotificacaoDTO dto) { // Usa NotificacaoDTO
        return this.notificacaoService.criarNotificacao(dto); // Service deve aceitar DTO
    }

    // --- ATUALIZAÇÃO (USANDO DTO) ---

    // atualiza uma notificacao existente pelo id
    @PutMapping("/{id}")
    public Notificacao atualizar(@PathVariable Long id, @RequestBody NotificacaoDTO dto) { // Usa NotificacaoDTO
        return this.notificacaoService.atualizarNotificacao(id, dto); // Service deve aceitar DTO
    }

    // --- DELEÇÃO ---

    // deleta uma notificacao pelo id
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.notificacaoService.deletarNotificacao(id);
    }
}