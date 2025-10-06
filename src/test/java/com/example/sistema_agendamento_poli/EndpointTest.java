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
    public void testGetUsuarios() {
        ResponseEntity<Usuario[]> response = restTemplate.getForEntity("/usuarios/listar", Usuario[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 0);
    }

    @Test
    public void testPostUsuario() {
        Usuario novo = new Usuario();
        novo.setNome("Vinicius");
        novo.setEmail("vinicius@teste.com");

        ResponseEntity<Void> response = restTemplate.postForEntity("/usuarios", novo, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getClass());
    }
}


