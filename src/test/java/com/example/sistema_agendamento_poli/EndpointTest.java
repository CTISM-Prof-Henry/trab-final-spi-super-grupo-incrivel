package com.example.sistema_agendamento_poli;

import com.example.sistema_agendamento_poli.usuario.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testPostUsuario() {
        Usuario novo = new Usuario();
        novo.setNome("Vinicius");
        novo.setEmail("vinicius@teste.com");
        novo.setSenha("123456");
        novo.setTipo("Aluno");
        novo.setIdentificador("20250001");
        novo.setTelefone("55999999999");

        ResponseEntity<Void> response = restTemplate.postForEntity("/usuarios", novo, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    public void testGetUsuarios() {
        ResponseEntity<Usuario[]> response = restTemplate.getForEntity("/usuarios/listar", Usuario[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Usuario[] usuarios = response.getBody();

        System.out.println("\n=== Usuários cadastrados ===");
        if (usuarios.length == 0) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            for (Usuario u : usuarios) {
                System.out.printf("ID: %d | Nome: %s | Email: %s | Tipo: %s | Identificador: %s%n",
                        u.getId(), u.getNome(), u.getEmail(), u.getTipo(), u.getIdentificador());
            }
        }
        System.out.println("============================\n");

        assertTrue(usuarios.length >= 0);
    }

}