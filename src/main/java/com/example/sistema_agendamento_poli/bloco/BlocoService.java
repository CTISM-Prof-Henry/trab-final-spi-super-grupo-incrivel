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

    public Bloco criarBloco(Bloco bloco) {

        return blocoRepository.save(bloco);
    }

    public List<Bloco> listarBlocos() {

        return blocoRepository.findAll();
    }

    public Optional<Bloco> buscarPorId(Long id) {

        return blocoRepository.findById(id);
    }

    public Bloco atualizarBloco(Long id, Bloco blocoAtualizado) {
        return blocoRepository.findById(id).map(bloco -> {

            bloco.setNome(blocoAtualizado.getNome());
            bloco.setQuantidadeSalas(blocoAtualizado.getQuantidadeSalas());
            bloco.setSalas(blocoAtualizado.getSalas());
            return blocoRepository.save(bloco);

        }).orElseThrow(() -> new RuntimeException("Bloco não encontrado com a id: " + id));
    }

    public void deletarBloco(Long id) {

        if (blocoRepository.existsById(id)) {
            blocoRepository.deleteById(id);
        } else {

            throw new RuntimeException("Bloco não encontrado com a id: " + id);
        }
    }
}
