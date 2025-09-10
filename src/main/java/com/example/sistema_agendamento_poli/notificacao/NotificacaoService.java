package com.example.sistema_agendamento_poli.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    @Autowired
    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public Notificacao criarNotificacao(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarNotificacoes() {
        return notificacaoRepository.findAll();
    }

    public Optional<Notificacao> buscarNotificacaoPorId(Long id) {
        return notificacaoRepository.findById(id);
    }

    public Notificacao atualizarNotificacao(Long id, Notificacao notificacaoAtualizada) {
        return notificacaoRepository.findById(id)
                .map(notificacao -> {

                    notificacao.setMensagem(notificacaoAtualizada.getMensagem());
                    notificacao.setUsuario(notificacaoAtualizada.getUsuario());
                    notificacao.setAgenda(notificacaoAtualizada.getAgenda());

                    return notificacaoRepository.save(notificacao);
                })
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada com o Id: " + id));
    }

    public void deletarNotificacao(Long id) {
        if (notificacaoRepository.existsById(id)) {
            notificacaoRepository.deleteById(id);
        } else {
            throw new RuntimeException("A notificação não existe para o id: " + id);
        }
    }
}
