package com.example.sistema_agendamento_poli.bloco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlocoService {

    private final BlocoRepository blocoRepository;

    @Autowired
    public BlocoService(BlocoRepository blocoRepository) {
        this.blocoRepository = blocoRepository;
    }

    // --- CRIAÇÃO (ACEITA DTO) ---
    public Bloco criarBloco(BlocoDTO blocoDTO) {
        // Converte o DTO recebido para a entidade Bloco usando o Mapper
        Bloco bloco = BlocoMapper.toEntity(blocoDTO);

        return blocoRepository.save(bloco);
    }

    // --- ATUALIZAÇÃO (ACEITA DTO) ---
    public Bloco atualizarBloco(Long id, BlocoDTO blocoDTO) {
        return blocoRepository.findById(id).map(blocoExistente -> {

            blocoExistente.setNome(blocoDTO.getNome());
            blocoExistente.setQt_Salas(blocoDTO.getQt_Salas());

            // Mantemos o relacionamento 'salas' intacto, pois ele não veio no DTO.
            return blocoRepository.save(blocoExistente);

        }).orElseThrow(() -> new RuntimeException("Bloco não encontrado com a id: " + id));
    }

    // --- CONSULTA E DELEÇÃO (INALTERADOS) ---

    public List<Bloco> listarBlocos() {
        return blocoRepository.findAll();
    }

    public Optional<Bloco> buscarPorId(Long id) {
        return blocoRepository.findById(id);
    }

    public void deletarBloco(Long id) {
        if (blocoRepository.existsById(id)) {
            blocoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Bloco não encontrado com a id: " + id);
        }
    }
}