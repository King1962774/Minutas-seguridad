package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.NovedadTurno;
import com.minutas.repository.NovedadTurnoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteNovedadTurnoRepository implements NovedadTurnoRepository {
    @Override
    public void save(NovedadTurno n) {
        String sql = "INSERT INTO novedad_turno (id_conjunto, id_turno, categoria, descripcion) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, n.getIdConjunto());
            pstmt.setInt(2, n.getIdTurno());
            pstmt.setString(3, n.getCategoria());
            pstmt.setString(4, n.getDescripcion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NovedadTurno> findByTurno(int idTurno) {
        List<NovedadTurno> lista = new ArrayList<>();
        String sql = "SELECT * FROM novedad_turno WHERE id_turno = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTurno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                NovedadTurno n = new NovedadTurno();
                n.setId(rs.getInt("id"));
                n.setIdConjunto(rs.getInt("id_conjunto"));
                n.setIdTurno(rs.getInt("id_turno"));
                n.setCategoria(rs.getString("categoria"));
                n.setDescripcion(rs.getString("descripcion"));
                n.setCreatedAt(rs.getString("created_at"));
                lista.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
