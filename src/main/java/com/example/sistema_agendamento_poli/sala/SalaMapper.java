package com.example.sistema_agendamento_poli.sala;

import com.example.sistema_agendamento_poli.bloco.Bloco;

public final class SalaMapper { // <--- Adicionado 'final'

    /**
     * Construtor privado para evitar a instanciação da classe utilitária.
     */
    private SalaMapper() { // <--- Construtor privado adicionado
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    public static SalaDTO toDTO(Sala sala) {
        if (sala == null) {
            return null;
        }

        return SalaDTO.builder()
                .id(sala.getId())
                .codigo(sala.getCodigo())
                .nome(sala.getNome())
                .andar(sala.getAndar())
                .blocoId(sala.getBloco().getId()) // extrai apenas o ID do bloco
                .build();
    }

    public static Sala toEntity(SalaDTO dto, Bloco bloco) {
        if (dto == null || bloco == null) {
            return null;
        }

        Sala sala = new Sala();
        sala.setId(dto.getId()); // geralmente null em criação, mas útil para updates
        sala.setCodigo(dto.getCodigo());
        sala.setNome(dto.getNome());
        sala.setAndar(dto.getAndar());
        sala.setBloco(bloco); // o objeto Bloco já deve ter sido buscado antes (por ID)

        return sala;
    }
}