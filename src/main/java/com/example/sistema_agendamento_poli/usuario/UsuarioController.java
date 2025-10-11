package com.example.sistema_agendamento_poli.usuario;

// Imports do Spring Web otimizados
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {this.usuarioService = usuarioService;}

    // --- MÉTODOS DE CONSULTA (Retornam Entidade) ---

    @GetMapping("/listar")
    public List<Usuario> listar() {
        return this.usuarioService.listarUsuarios();
    }

    @GetMapping("/busca/email/{email}")
    public Optional<Usuario> usuario(@PathVariable String email){
        return this.usuarioService.buscarUsuario(email);
    }

    @GetMapping("/busca/identificador/{identificador}")
    public Optional<Usuario> usuarioPorIdentificador(@PathVariable String identificador){
        return this.usuarioService.buscarUsuarioPorIdentificador(identificador);
    }

    @PostMapping("/print-json")
    public void printJson(@RequestBody String json){
        System.out.println(json);
    }

    // --- CRIAÇÃO (USANDO DTO) ---
    @PostMapping()
    public void criarUsuario(@RequestBody UsuarioDTO dto){ // Alterado para DTO
        this.usuarioService.criarUsuario(dto);
    }

    // --- ATUALIZAÇÃO (USANDO DTO) ---
    @PutMapping("/{id}")
    public void atualizar(@RequestBody UsuarioDTO dto, @PathVariable Long id){ // Alterado para DTO
        this.usuarioService.atualizarUsuario(id, dto);
    }

    // --- DELEÇÃO ---
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        this.usuarioService.deletarUsuario(id);
    }
}