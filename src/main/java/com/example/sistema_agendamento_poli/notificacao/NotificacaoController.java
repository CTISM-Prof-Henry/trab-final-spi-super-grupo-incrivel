package com.example.sistema_agendamento_poli.notificacao;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    // retorna todas as notificacoes
    @GetMapping("/listar")
    public List<Notificacao> listar() {
        return this.notificacaoService.listarNotificacoes();
    }

    // retorna uma notificacao especifica pelo id
    @GetMapping("/{id}")
    public Optional<Notificacao> buscarPorId(@PathVariable Long id) {
        return this.notificacaoService.buscarNotificacaoPorId(id);
    }

    // cria uma nova notificacao
    @PostMapping
    public Notificacao criar(@RequestBody Notificacao notificacao) {
        return this.notificacaoService.criarNotificacao(notificacao);
    }

    // atualiza uma notificacao existente pelo id
    @PutMapping("/{id}")
    public Notificacao atualizar(@PathVariable Long id, @RequestBody Notificacao notificacao) {
        return this.notificacaoService.atualizarNotificacao(id, notificacao);
    }

    // deleta uma notificacao pelo id
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.notificacaoService.deletarNotificacao(id);
    }
}
