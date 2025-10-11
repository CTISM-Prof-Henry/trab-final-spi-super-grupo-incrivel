package com.example.sistema_agendamento_poli.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
// Imports de validation explícitos
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
// Imports do lombok explícitos
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // já inclui @Getter, @Setter, @ToString, @EqualsAndHashCode, etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    @Null(message = "O id deve ser nulo na criação e atualização; é gerado pelo sistema.")
    private Long id;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
    private String nome;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres.")
    private String telefone;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido.")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres.")
    private String email;

    @NotBlank(message = "Tipo é obrigatório (Aluno, Professor, Administrador).")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres.")
    private String tipo;

    @NotBlank(message = "Identificador é obrigatório (matrícula/contrato).")
    @Size(max = 50, message = "Identificador deve ter no máximo 50 caracteres.")
    private String identificador;

    /**
     * Campo só para ENTRADA (POST/PUT). Nunca aparece em resposta JSON.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, max = 255, message = "Senha deve ter entre 6 e 255 caracteres.")
    private String senha;
}