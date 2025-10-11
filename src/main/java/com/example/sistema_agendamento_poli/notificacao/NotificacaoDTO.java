package com.example.sistema_agendamento_poli.notificacao;

// Imports de validation explícitos
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
// Imports do lombok explícitos
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacaoDTO {

    @Null(message = "O id deve ser gerado pelo sistema.")
    private Long id;

    @NotBlank(message = "Mensagem obrigatoria.")
    private String mensagem;

    @NotNull(message = "Usuario obrigatorio.")
    private Long usuarioId;

    @NotNull(message = "Agenda obrigatoria.")
    private Long agendaId;
}