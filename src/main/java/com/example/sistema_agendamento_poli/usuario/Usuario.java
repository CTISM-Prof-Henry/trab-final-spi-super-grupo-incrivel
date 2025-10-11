package com.example.sistema_agendamento_poli.usuario;

// Imports do persistence otimizados
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Imports do lombok otimizados
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 20)
    private String telefone;

    @NonNull
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NonNull
    @Column(nullable = false, length = 255)
    private String senha;

    @NonNull
    @Column(nullable = false, length = 50)
    private String tipo; // Aluno | Professor | Administrador

    @NonNull
    @Column(nullable = false, unique = true, length = 50)
    private String identificador; // Matrícula ou contrato

    /**
     * Construtor para ser usado em conversões DTO->Entidade para criar referência
     * de chave estrangeira (FK) apenas com o ID.
     */
    public Usuario(Long id) {
        this.id = id;
    }
}