package com.example.sistema_agendamento_poli.notificacao;

import com.example.sistema_agendamento_poli.agenda.Agenda;
import com.example.sistema_agendamento_poli.usuario.Usuario;
import com.example.sistema_agendamento_poli.agenda.AgendaRepository;
import com.example.sistema_agendamento_poli.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    // Repositórios necessários para buscar as Entidades FKs (Usuario e Agenda)
    private final UsuarioRepository usuarioRepository;
    private final AgendaRepository agendaRepository;

    public NotificacaoService(
            NotificacaoRepository notificacaoRepository,
            UsuarioRepository usuarioRepository,
            AgendaRepository agendaRepository) {

        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.agendaRepository = agendaRepository;
    }

    // --- CRIAÇÃO (ACEITA DTO) ---
    public Notificacao criarNotificacao(NotificacaoDTO dto) {
        // 1. Busca as entidades FKs (Usuario e Agenda) pelo ID do DTO
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o Id: " + dto.getUsuarioId()));

        Agenda agenda = agendaRepository.findById(dto.getAgendaId())
                .orElseThrow(() -> new RuntimeException("Agenda não encontrada com o Id: " + dto.getAgendaId()));

        // 2. Converte o DTO para a Entidade, injetando as FKs
        Notificacao notificacao = NotificacaoMapper.toEntity(dto, usuario, agenda);

        // 3. Salva a entidade
        return notificacaoRepository.save(notificacao);
    }

    // --- ATUALIZAÇÃO (ACEITA DTO) ---
    public Notificacao atualizarNotificacao(Long id, NotificacaoDTO dto) {
        return notificacaoRepository.findById(id)
                .map(notificacaoExistente -> {

                    // A mensagem é o único campo não-FK que geralmente muda
                    notificacaoExistente.setMensagem(dto.getMensagem());

                    // Se o UsuarioId ou AgendaId vierem no DTO, é necessário
                    // buscar as novas entidades para atualizar as FKs,
                    // mas em um cenário de notificação, essas FKs raramente mudam.
                    // Vamos atualizar apenas a mensagem.

                    return notificacaoRepository.save(notificacaoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada com o Id: " + id));
    }

    // --- MÉTODOS INALTERADOS ---

    public List<Notificacao> listarNotificacoes() {
        return notificacaoRepository.findAll();
    }

    public Optional<Notificacao> buscarNotificacaoPorId(Long id) {
        return notificacaoRepository.findById(id);
    }

    public void deletarNotificacao(Long id) {
        if (notificacaoRepository.existsById(id)) {
            notificacaoRepository.deleteById(id);
        } else {
            throw new RuntimeException("A notificação não existe para o id: " + id);
        }
    }
}