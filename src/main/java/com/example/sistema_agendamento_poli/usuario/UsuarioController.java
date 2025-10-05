package com.example.sistema_agendamento_poli.usuario;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {this.usuarioService = usuarioService;}

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

    @PostMapping()
    public void criarUsuario(@RequestBody Usuario usuario){
        this.usuarioService.criarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public void atualizar(@RequestBody Usuario usuario, @PathVariable Long id){
        this.usuarioService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        this.usuarioService.deletarUsuario(id);
    }
}
