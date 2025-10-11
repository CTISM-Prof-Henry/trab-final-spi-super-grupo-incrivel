package com.example.sistema_agendamento_poli.agenda;

import com.example.sistema_agendamento_poli.sala.Sala;
import com.example.sistema_agendamento_poli.usuario.Usuario;

//imports do persistence
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//imports do lombok
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Imports de data/hora
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: sala_id
    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    @NonNull
    private Sala sala;

    // FK: usuario_id
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NonNull
    private Usuario usuario;

    // date: data (Refatorado para java.time.LocalDate)
    @Column(name = "data", nullable = false)
    @NonNull
    private LocalDate data; // Tipo alterado de Date para LocalDate

    // time: horario_inicio (Refatorado para java.time.LocalTime)
    @Column(name = "horario_inicio", nullable = false)
    @NonNull
    private LocalTime horarioInicio; // Tipo alterado de Time para LocalTime

    // time: horario_fim (Refatorado para java.time.LocalTime)
    @Column(name = "horario_fim", nullable = false)
    @NonNull
    private LocalTime horarioFim; // Tipo alterado de Time para LocalTime

    // string: status ("Disponível" | "Ocupado")
    @Column(name = "status", nullable = false, length = 32)
    @NonNull
    private String status;

}