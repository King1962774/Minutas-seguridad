package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.Incidente;
import com.minutas.repository.IncidenteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteIncidenteRepository implements IncidenteRepository {
    @Override
    public void save(Incidente inc) {
        String sql = "INSERT INTO incidente (id_conjunto, id_turno, id_usuario, tipo, descripcion, atendido) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, inc.getIdConjunto());
            pstmt.setInt(2, inc.getIdTurno());
            pstmt.setInt(3, inc.getIdUsuario());
            pstmt.setString(4, inc.getTipo());
            pstmt.setString(5, inc.getDescripcion());
            pstmt.setInt(6, inc.getAtendido());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Incidente> findAll(int idConjunto) {
        List<Incidente> lista = new ArrayList<>();
        String sql = "SELECT * FROM incidente WHERE id_conjunto = ? ORDER BY id DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idConjunto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Incidente inc = new Incidente();
                inc.setId(rs.getInt("id"));
                inc.setIdConjunto(rs.getInt("id_conjunto"));
                inc.setIdTurno(rs.getInt("id_turno"));
                inc.setIdUsuario(rs.getInt("id_usuario"));
                inc.setTipo(rs.getString("tipo"));
                inc.setDescripcion(rs.getString("descripcion"));
                inc.setAtendido(rs.getInt("atendido"));
                inc.setCreatedAt(rs.getString("created_at"));
                lista.add(inc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
