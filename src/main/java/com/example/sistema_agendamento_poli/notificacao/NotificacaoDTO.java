package com.example.sistema_agendamento_poli.notificacao;

import jakarta.validation.constraints.*;
import lombok.*;

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
