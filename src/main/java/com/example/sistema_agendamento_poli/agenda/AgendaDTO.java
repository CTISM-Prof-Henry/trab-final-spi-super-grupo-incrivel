package com.example.sistema_agendamento_poli.agenda;

// Imports de validation explícitos
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

// Imports do lombok explícitos
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Imports de data/hora modernos
import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate data; // Alterado de java.sql.Date para java.time.LocalDate

    @NotNull(message = "Horario de inicio obrigatorio.")
    private LocalTime horarioInicio; // Alterado de java.sql.Time para java.time.LocalTime

    @NotNull(message = "Horario de fim obrigatorio.")
    private LocalTime horarioFim; // Alterado de java.sql.Time para java.time.LocalTime

    @NotBlank(message = "Status obrigatorio (Disponivel, Ocupado, Pendente, Cancelado).")
    @Size(max = 32, message = "Status deve ter no maximo 32 caracteres.")
    private String status;
}