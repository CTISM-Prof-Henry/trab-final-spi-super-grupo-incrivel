package com.example.sistema_agendamento_poli.geral.model.pedidoagenda;

import com.example.sistema_agendamento_poli.geral.model.sala.Sala;
import com.example.sistema_agendamento_poli.geral.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="pedido_agenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class PedidoAgenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    @NonNull
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NonNull
    private Usuario usuario;

    @NonNull
    private Date dia_start_at;

    @NonNull
    private Date dia_end_at;

    @NonNull
    private Time hora_start_at;

    @NonNull
    private Time hora_end_at;

    @NonNull
    private String status;
}
