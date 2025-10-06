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

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuario(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarUsuarioPorIdentificador(String identificador) {
        return usuarioRepository.findByIdentificador(identificador);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {

            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setTelefone(usuarioAtualizado.getTelefone());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
            usuario.setTipo(usuarioAtualizado.getTipo());
            usuario.setIdentificador(usuarioAtualizado.getIdentificador());

            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o Id: " + id));
    }

    public void deletarUsuario(Long id){

        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }else{
            throw new RuntimeException("O usuário não existe para o id: "+id);
        }

        }

    }
