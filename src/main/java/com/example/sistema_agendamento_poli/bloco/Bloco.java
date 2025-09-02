package com.example.sistema_agendamento_poli.bloco;

import com.example.sistema_agendamento_poli.sala.Sala;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "bloco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Bloco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // string: nome
    @Column(name = "nome", nullable = false, length = 100)
    @NonNull
    private String nome;

    // int: quantidade_salas
    @Column(name = "quantidade_salas", nullable = false)
    @NonNull
    private Integer quantidadeSalas;

    // Relacionamento 1:N com Sala
    @OneToMany(mappedBy = "bloco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sala> salas;
}
