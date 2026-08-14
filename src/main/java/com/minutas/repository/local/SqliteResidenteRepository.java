package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.Residente;
import com.minutas.repository.ResidenteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteResidenteRepository implements ResidenteRepository {
    @Override
    public List<Residente> findAll(int idConjunto) {
        List<Residente> lista = new ArrayList<>();
        String sql = "SELECT * FROM residente WHERE id_conjunto = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idConjunto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Residente r = new Residente();
                r.setId(rs.getInt("id"));
                r.setIdConjunto(rs.getInt("id_conjunto"));
                r.setIdUnidad(rs.getInt("id_unidad"));
                r.setNombre(rs.getString("nombre"));
                r.setDocumento(rs.getString("documento"));
                r.setTelefono(rs.getString("telefono"));
                r.setEmail(rs.getString("email"));
                r.setTipo(rs.getString("tipo"));
                r.setActivo(rs.getInt("activo"));
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void save(Residente r) {
        String sql = "INSERT INTO residente (id_conjunto, id_unidad, nombre, documento, telefono, email, tipo, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, r.getIdConjunto());
            pstmt.setInt(2, r.getIdUnidad());
            pstmt.setString(3, r.getNombre());
            pstmt.setString(4, r.getDocumento());
            pstmt.setString(5, r.getTelefono());
            pstmt.setString(6, r.getEmail());
            pstmt.setString(7, r.getTipo());
            pstmt.setInt(8, r.getActivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
