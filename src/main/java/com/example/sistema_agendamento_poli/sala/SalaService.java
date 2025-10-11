package com.example.sistema_agendamento_poli.sala;

import com.example.sistema_agendamento_poli.bloco.Bloco;
import com.example.sistema_agendamento_poli.bloco.BlocoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final BlocoRepository blocoRepository; // Adicionado para buscar a FK

    @Autowired
    public SalaService(SalaRepository salaRepository, BlocoRepository blocoRepository) {
        this.salaRepository = salaRepository;
        this.blocoRepository = blocoRepository;
    }

    // --- CRIAÇÃO (ACEITA DTO) ---
    public Sala criarSala(SalaDTO dto) {
        // 1. Busca a entidade Bloco (FK) pelo ID do DTO
        Bloco bloco = blocoRepository.findById(dto.getBlocoId())
                .orElseThrow(() -> new RuntimeException("Bloco não encontrado com o id: " + dto.getBlocoId()));

        // 2. Converte o DTO para a Entidade, injetando a FK
        Sala sala = SalaMapper.toEntity(dto, bloco);

        return salaRepository.save(sala);
    }

    // --- ATUALIZAÇÃO (ACEITA DTO) ---
    public Sala atualizarSala(Long id, SalaDTO dto) {
        return salaRepository.findById(id).map(salaExistente -> {

            // Atualiza campos não-FK
            salaExistente.setCodigo(dto.getCodigo());
            salaExistente.setNome(dto.getNome());
            salaExistente.setAndar(dto.getAndar());

            // Se o ID do bloco for diferente, busca e atualiza a FK
            if (!salaExistente.getBloco().getId().equals(dto.getBlocoId())) {
                Bloco novoBloco = blocoRepository.findById(dto.getBlocoId())
                        .orElseThrow(() -> new RuntimeException("Bloco não encontrado com o id: " + dto.getBlocoId()));
                salaExistente.setBloco(novoBloco);
            }

            return salaRepository.save(salaExistente);
        }).orElseThrow(() -> new RuntimeException("Sala não encontrada com o id: " + id));
    }

    // --- CONSULTA E DELEÇÃO (INALTERADOS) ---

    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> buscarPorId(Long id) {
        return salaRepository.findById(id);
    }

    public Optional<Sala> buscarPorCodigo(String codigo) {
        // Pressupõe que findByCodigo existe em SalaRepository
        return salaRepository.findByCodigo(codigo);
    }

    public void deletarSala(Long id) {
        if (salaRepository.existsById(id)) {
            salaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sala não encontrada com o id: " + id);
        }
    }
}