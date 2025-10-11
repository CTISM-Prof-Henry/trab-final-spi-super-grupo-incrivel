package com.example.sistema_agendamento_poli.notificacao;

import com.example.sistema_agendamento_poli.agenda.Agenda;
import com.example.sistema_agendamento_poli.usuario.Usuario;
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