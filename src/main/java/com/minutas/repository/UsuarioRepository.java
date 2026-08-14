package com.minutas.repository;

import com.minutas.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findAll(int idConjunto);
    void save(Usuario usuario);
    void update(Usuario usuario);
}
