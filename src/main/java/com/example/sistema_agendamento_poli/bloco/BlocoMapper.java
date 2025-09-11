package com.example.sistema_agendamento_poli.bloco;

public class BlocoMapper {

    public static BlocoDTO toDTO(Bloco bloco) {
        if (bloco == null) {
            return null;
        }

        return BlocoDTO.builder()
                .id(bloco.getId())
                .nome(bloco.getNome())
                .quantidadeSalas(bloco.getQuantidadeSalas())
                .build();
    }

    public static Bloco toEntity(BlocoDTO dto) {
        if (dto == null) {
            return null;
        }

        Bloco bloco = new Bloco();
        bloco.setId(dto.getId()); // útil para update
        bloco.setNome(dto.getNome());
        bloco.setQuantidadeSalas(dto.getQuantidadeSalas());

        // .setSalas() é ignorado aqui, pois não vem no DTO
        return bloco;
    }
}

