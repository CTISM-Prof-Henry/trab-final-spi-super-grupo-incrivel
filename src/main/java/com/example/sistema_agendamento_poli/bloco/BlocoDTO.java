package com.example.sistema_agendamento_poli.bloco;

import jakarta.validation.constraints.*;
import lombok.*;

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
    private Integer quantidadeSalas;
}
