package com.example.sistema_agendamento_poli.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // --- CRIAÇÃO (ACEITA DTO) ---
    public Usuario criarUsuario(UsuarioDTO dto) {
        // Converte o DTO para a Entidade
        Usuario novoUsuario = UsuarioMapper.toEntity(dto);


        return usuarioRepository.save(novoUsuario);
    }

    // --- ATUALIZAÇÃO (ACEITA DTO) ---
    public Usuario atualizarUsuario(Long id, UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {

            // Atualiza campos não-senha
            usuarioExistente.setNome(dto.getNome());
            usuarioExistente.setTelefone(dto.getTelefone());
            usuarioExistente.setEmail(dto.getEmail());
            usuarioExistente.setTipo(dto.getTipo());
            usuarioExistente.setIdentificador(dto.getIdentificador());

            // Lógica para SENHA: Só atualiza se a senha for fornecida no DTO
            if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
                // AQUI deve vir a lógica de hashing da nova senha
                usuarioExistente.setSenha(dto.getSenha()); // Apenas para compilar, sem hashing
            }

            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o Id: " + id));
    }

    // --- CONSULTA E DELEÇÃO (INALTERADOS) ---

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarUsuario(String email) {
        // Pressupõe que findByEmail existe em UsuarioRepository
        return usuarioRepository.findByEmail(email);
    }


    public Optional<Usuario> buscarUsuarioPorIdentificador(String identificador) {
        // Pressupõe que findByIdentificador existe em UsuarioRepository
        return usuarioRepository.findByIdentificador(identificador);
    }

    public void deletarUsuario(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }else{
            throw new RuntimeException("O usuário não existe para o id: "+id);
        }
    }
}