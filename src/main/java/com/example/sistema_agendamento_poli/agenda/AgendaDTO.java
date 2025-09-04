package com.example.sistema_agendamento_poli.agenda;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDTO {

    @Null(message = "O id deve ser gerado pelo sistema.")
    private Long id;

    @NotNull(message = "Sala obrigatoria.")
    private Long salaId;

    @NotNull(message = "Usuario obrigatorio.")
    private Long usuarioId;

    @NotNull(message = "Data obrigatoria.")
    private Date data;

    @NotNull(message = "Horario de inicio obrigatorio.")
    private Time horarioInicio;

    @NotNull(message = "Horario de fim obrigatorio.")
    private Time horarioFim;

    @NotBlank(message = "Status obrigatorio (Disponivel, Ocupado, Pendente, Cancelado).")
    @Size(max = 32, message = "Status deve ter no maximo 32 caracteres.")
    private String status;
}
