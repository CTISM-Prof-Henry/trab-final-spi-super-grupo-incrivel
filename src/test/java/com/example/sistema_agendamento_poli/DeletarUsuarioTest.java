package com.example.sistema_agendamento_poli;

import com.example.sistema_agendamento_poli.usuario.Usuario;
import com.example.sistema_agendamento_poli.usuario.UsuarioDTO;
import com.example.sistema_agendamento_poli.usuario.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DeletarUsuarioTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void testDeleteUsuario() {

        UsuarioDTO dtoDeletar = new UsuarioDTO();
        dtoDeletar.setNome("deletarExemplo");
        dtoDeletar.setEmail("deletar@teste.com");
        dtoDeletar.setSenha("123456");
        dtoDeletar.setTipo("Aluno");
        dtoDeletar.setIdentificador("20250002");
        dtoDeletar.setTelefone("55999999998");
        Usuario deletarExemplo = usuarioService.criarUsuario(dtoDeletar);
        assertNotNull(deletarExemplo.getId(), "Usuário deletarExemplo não foi criado");

        System.out.println("Usuários antes da deleção:");
        List<Usuario> todosAntes = usuarioService.listarUsuarios();
        todosAntes.forEach(u -> System.out.println(u.getId() + " - " + u.getNome() + " - " + u.getEmail()));

        usuarioService.deletarUsuario(deletarExemplo.getId());

        System.out.println("Usuários após deletar 'deletarExemplo':");
        List<Usuario> todosDepois = usuarioService.listarUsuarios();
        todosDepois.forEach(u -> System.out.println(u.getId() + " - " + u.getNome() + " - " + u.getEmail()));

        Optional<Usuario> deletado = usuarioService.buscarUsuarioPorId(deletarExemplo.getId());
        assertTrue(deletado.isEmpty(), "'deletarExemplo' ainda existe após delete");
    }
}