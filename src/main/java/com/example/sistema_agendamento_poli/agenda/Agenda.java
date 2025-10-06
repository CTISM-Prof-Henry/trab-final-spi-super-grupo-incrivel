package com.example.sistema_agendamento_poli.agenda;

import com.example.sistema_agendamento_poli.sala.Sala;
import com.example.sistema_agendamento_poli.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

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

    // date: data
    @Column(name = "data", nullable = false)
    @NonNull
    private Date data;

    // time: horario_inicio
    @Column(name = "horario_inicio", nullable = false)
    @NonNull
    private Time horarioInicio;

    // time: horario_fim
    @Column(name = "horario_fim", nullable = false)
    @NonNull
    private Time horarioFim;

    // string: status ("Disponível" | "Ocupado")
    @Column(name = "status", nullable = false, length = 32)
    @NonNull
    private String status;

}
