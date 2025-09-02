package com.example.sistema_agendamento_poli.notificacao;

import com.example.sistema_agendamento_poli.agenda.Agenda;
import com.example.sistema_agendamento_poli.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // STRING: mensagem
    @NonNull
    @Column(name = "mensagem", nullable = false)
    private String mensagem;

    // FK: usuario_id (USUARIO ||--o{ NOTIFICACAO : recebe)
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NonNull
    private Usuario usuario;

    // FK: agenda_id (AGENDA ||--o{ NOTIFICACAO : gera)
    @ManyToOne(optional = false)
    @JoinColumn(name = "agenda_id", nullable = false)
    @NonNull
    private Agenda agenda;
}
