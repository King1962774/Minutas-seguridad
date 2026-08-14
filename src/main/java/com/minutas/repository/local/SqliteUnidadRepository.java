package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.Unidad;
import com.minutas.repository.UnidadRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUnidadRepository implements UnidadRepository {
    @Override
    public List<Unidad> findAll(int idConjunto) {
        List<Unidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidad WHERE id_conjunto = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idConjunto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Unidad u = new Unidad();
                u.setId(rs.getInt("id"));
                u.setIdConjunto(rs.getInt("id_conjunto"));
                u.setTorre(rs.getString("torre"));
                u.setNumero(rs.getString("numero"));
                u.setTipo(rs.getString("tipo"));
                u.setCoeficiente(rs.getDouble("coeficiente"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void save(Unidad u) {
        String sql = "INSERT INTO unidad (id_conjunto, torre, numero, tipo, coeficiente) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, u.getIdConjunto());
            pstmt.setString(2, u.getTorre());
            pstmt.setString(3, u.getNumero());
            pstmt.setString(4, u.getTipo());
            pstmt.setDouble(5, u.getCoeficiente());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
