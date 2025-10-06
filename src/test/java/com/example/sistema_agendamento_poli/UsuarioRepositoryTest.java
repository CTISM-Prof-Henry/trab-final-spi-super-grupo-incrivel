package com.example.sistema_agendamento_poli;


import com.example.sistema_agendamento_poli.usuario.Usuario;
import com.example.sistema_agendamento_poli.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveInserirEListarUsuarios() {

        Usuario usuario = new Usuario();
        usuario.setNome("Vinicius Abaddy");
        usuario.setTipo("ADM");
        usuario.setIdentificador("20251234");
        usuario.setEmail("vinicius@teste.com");
        usuario.setSenha("123456");

        usuarioRepository.save(usuario);

        List<Usuario> usuarios = usuarioRepository.findAll();

        System.out.println("=== USUÁRIOS NO BANCO ===");
        for (Usuario u : usuarios) {
            System.out.println(   "ID: " + u.getId() +
                    " | Nome: " + u.getNome() +
                    " | Tipo: " + u.getTipo() +
                    " | Identificador: " + u.getIdentificador() +
                    " | Email: " + u.getEmail() +
                    " | Senha: " + u.getSenha()
            );
        }

        assert !usuarios.isEmpty();
    }
}