package com.example.sistema_agendamento_poli.sala;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaDTO {

    @Null(message = "O id deve ser gerado pelo sistema.")
    private Long id;

    @NotBlank(message = "O código da sala é obrigatório.")
    @Size(max = 50, message = "O código deve ter no máximo 50 caracteres.")
    private String codigo;

    @NotBlank(message = "O nome da sala é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotNull(message = "O andar é obrigatório.")
    private Integer andar;

    @NotNull(message = "O bloco é obrigatório.")
    private Long blocoId; // referência ao bloco (evita expor o objeto inteiro)
}