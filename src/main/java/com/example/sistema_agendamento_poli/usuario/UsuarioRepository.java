package com.example.sistema_agendamento_poli.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca usuario por email
    // Retorna o usuario com o email informado
    Optional<Usuario> findByEmail(String email);

    // Verifica existencia por email
    // Retorna true se existe usuario com esse email
    boolean existsByEmail(String email);

    // Busca usuario por identificador
    // Retorna o usuario pela matricula ou contrato
    Optional<Usuario> findByIdentificador(String identificador);

    // Verifica existencia por identificador
    // Retorna true se existe usuario com essa matricula ou contrato
    boolean existsByIdentificador(String identificador);

    // Busca por email ou identificador
    // Retorna o usuario que casar com email ou identificador (login flexivel)
    Optional<Usuario> findByEmailOrIdentificador(String email, String identificador);

    List<Usuario> id(Long id);

}
