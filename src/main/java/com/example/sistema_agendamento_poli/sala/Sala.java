package com.example.sistema_agendamento_poli.sala;

import com.example.sistema_agendamento_poli.bloco.Bloco;
// Imports do persistence otimizados
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
// Imports do lombok otimizados
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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

    /**
     * Construtor para ser usado em conversões DTO->Entidade para criar referência
     * de chave estrangeira (FK) apenas com o ID.
     */
    public Sala(Long id) {
        this.id = id;
    }
}