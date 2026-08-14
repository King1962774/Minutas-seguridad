package com.minutas.service;

import com.minutas.model.Usuario;
import com.minutas.repository.UsuarioRepository;
import com.minutas.repository.local.SqliteUsuarioRepository;

import java.util.Optional;

public class AutenticacionService {
    private final UsuarioRepository usuarioRepository;
    private static Usuario usuarioActual;

    public AutenticacionService() {
        this.usuarioRepository = new SqliteUsuarioRepository();
    }

    public AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> login(String username, String password) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            usuarioActual = userOpt.get();
            return userOpt;
        }
        return Optional.empty();
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario u) {
        usuarioActual = u;
    }
}
