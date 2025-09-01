package com.example.sistema_agendamento_poli.geral.model.sala;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="sala")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String bloco;

    @NonNull
    private String n_sala;

    @NonNull
    private int andar;
}
