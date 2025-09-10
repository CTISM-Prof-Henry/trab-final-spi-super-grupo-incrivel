package com.example.sistema_agendamento_poli.sala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    @Autowired
    public SalaService(SalaRepository salaRepository) {

        this.salaRepository = salaRepository;
    }

    public Sala criarSala(Sala sala) {

        return salaRepository.save(sala);
    }

    public List<Sala> listarSalas() {

        return salaRepository.findAll();
    }

    public Optional<Sala> buscarPorId(Long id) {

        return salaRepository.findById(id);
    }

    public Optional<Sala> buscarPorCodigo(String codigo) {

        return salaRepository.findByCodigo(codigo);
    }

    public Sala atualizarSala(Long id, Sala salaAtualizada) {
        return salaRepository.findById(id).map(sala -> {

            sala.setCodigo(salaAtualizada.getCodigo());
            sala.setNome(salaAtualizada.getNome());
            sala.setAndar(salaAtualizada.getAndar());
            sala.setBloco(salaAtualizada.getBloco());
            return salaRepository.save(sala);
        }).orElseThrow(() -> new RuntimeException("Sala não encontrada com o id: " + id));
    }

    public void deletarSala(Long id) {
        if (salaRepository.existsById(id)) {
            salaRepository.deleteById(id);
        } else {

            throw new RuntimeException("Sala não encontrada com o id: " + id);
        }
    }
}
