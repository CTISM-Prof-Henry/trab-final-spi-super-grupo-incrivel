package com.example.sistema_agendamento_poli.usuario;

public final class UsuarioMapper { // <--- Adicionado 'final'

    /**
     * Construtor privado para evitar a instanciação da classe utilitária.
     */
    private UsuarioMapper() { // <--- Construtor privado adicionado
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .telefone(usuario.getTelefone())
                .email(usuario.getEmail())
                .tipo(usuario.getTipo())
                .identificador(usuario.getIdentificador())
                // senha é ignorada na saída, como indicado pela anotação @JsonProperty WRITE_ONLY
                .build();
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId()); // normalmente evitado no create, mas útil no update
        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEmail(dto.getEmail());
        usuario.setTipo(dto.getTipo());
        usuario.setIdentificador(dto.getIdentificador());

        // só seta senha se estiver presente (evita sobrescrever com null em updates parciais)
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(dto.getSenha());
        }

        return usuario;
    }
}