package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.RegistroVisita;
import com.minutas.repository.RegistroVisitaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteRegistroVisitaRepository implements RegistroVisitaRepository {
    @Override
    public void save(RegistroVisita rv) {
        String sql = "INSERT INTO registro_visita (id_conjunto, id_visitante, id_unidad, id_residente, id_turno, vehiculo_placa, observacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rv.getIdConjunto());
            pstmt.setInt(2, rv.getIdVisitante());
            pstmt.setInt(3, rv.getIdUnidad());
            pstmt.setInt(4, rv.getIdResidente());
            pstmt.setInt(5, rv.getIdTurno());
            pstmt.setString(6, rv.getVehiculoPlaca());
            pstmt.setString(7, rv.getObservacion());
            pstmt.setString(8, rv.getEstado() != null ? rv.getEstado() : "DENTRO");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RegistroVisita> findAll(int idConjunto) {
        List<RegistroVisita> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_visita WHERE id_conjunto = ? ORDER BY id DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idConjunto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public long countByTurno(int idTurno) {
        String sql = "SELECT COUNT(*) FROM registro_visita WHERE id_turno = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTurno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private RegistroVisita map(ResultSet rs) throws SQLException {
        RegistroVisita rv = new RegistroVisita();
        rv.setId(rs.getInt("id"));
        rv.setIdConjunto(rs.getInt("id_conjunto"));
        rv.setIdVisitante(rs.getInt("id_visitante"));
        rv.setIdUnidad(rs.getInt("id_unidad"));
        rv.setIdResidente(rs.getInt("id_residente"));
        rv.setIdTurno(rs.getInt("id_turno"));
        rv.setVehiculoPlaca(rs.getString("vehiculo_placa"));
        rv.setObservacion(rs.getString("observacion"));
        rv.setHoraEntrada(rs.getString("hora_entrada"));
        rv.setHoraSalida(rs.getString("hora_salida"));
        rv.setEstado(rs.getString("estado"));
        return rv;
    }
}
