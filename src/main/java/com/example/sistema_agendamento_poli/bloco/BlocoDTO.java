package com.example.sistema_agendamento_poli.bloco;

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
public class BlocoDTO {

    @Null(message = "O id deve ser gerado pelo sistema.")
    private Long id;

    @NotBlank(message = "Nome obrigatorio.")
    @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres.")
    private String nome;

    @NotNull(message = "Quantidade de salas obrigatoria.")
    private Integer qt_Salas;
}