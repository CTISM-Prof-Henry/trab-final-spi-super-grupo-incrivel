package com.example.sistema_agendamento_poli.usuario;

import jakarta.persistence.*;
import lombok.*;

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
}
