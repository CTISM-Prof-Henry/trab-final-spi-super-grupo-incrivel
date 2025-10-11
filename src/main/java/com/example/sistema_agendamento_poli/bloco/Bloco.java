package com.example.sistema_agendamento_poli.bloco;

import com.example.sistema_agendamento_poli.sala.Sala;

//imports persistance
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//imports lombok
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    @Column(name = "qt_salas", nullable = false)
    @NonNull
    private Integer qt_Salas;

    // Relacionamento 1:N com Sala
    @OneToMany(mappedBy = "bloco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sala> salas;
}