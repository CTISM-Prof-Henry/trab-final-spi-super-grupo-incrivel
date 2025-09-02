package com.example.sistema_agendamento_poli.sala;

import com.example.sistema_agendamento_poli.bloco.Bloco;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sala")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // código da sala
    @Column(name = "codigo", nullable = false, length = 50)
    @NonNull
    private String codigo;

    // nome da sala
    @Column(name = "nome", nullable = false, length = 100)
    @NonNull
    private String nome;

    // andar da sala
    @Column(name = "andar", nullable = false)
    @NonNull
    private Integer andar;

    // relacionamento com Bloco (FK: bloco_id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "bloco_id", nullable = false)
    @NonNull
    private Bloco bloco;
}
