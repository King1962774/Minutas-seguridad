package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.Usuario;
import com.minutas.repository.UsuarioRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteUsuarioRepository implements SqliteUsuarioRepositoryInterface, UsuarioRepository { // keeping compatibility

    @Override
    public Optional<Usuario> findByUsername(String username) {
        String sql = "SELECT * FROM usuario WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setIdConjunto(rs.getInt("id_conjunto"));
                u.setNombre(rs.getString("nombre"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                u.setActivo(rs.getInt("activo"));
                return Optional.of(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll(int idConjunto) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE id_conjunto = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idConjunto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setIdConjunto(rs.getInt("id_conjunto"));
                u.setNombre(rs.getString("nombre"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                u.setActivo(rs.getInt("activo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void save(Usuario u) {
        String sql = "INSERT INTO usuario (id_conjunto, nombre, username, password, rol, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, u.getIdConjunto());
            pstmt.setString(2, u.getNombre());
            pstmt.setString(3, u.getUsername());
            pstmt.setString(4, u.getPassword());
            pstmt.setString(5, u.getRol());
            pstmt.setInt(6, u.getActivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Usuario u) {
        String sql = "UPDATE usuario SET nombre = ?, username = ?, password = ?, rol = ?, activo = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, u.getNombre());
            pstmt.setString(2, u.getUsername());
            pstmt.setString(3, u.getPassword());
            pstmt.setString(4, u.getRol());
            pstmt.setInt(5, u.getActivo());
            pstmt.setInt(6, u.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
interface SqliteUsuarioRepositoryInterface {}
