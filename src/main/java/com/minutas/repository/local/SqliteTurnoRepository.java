package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.Turno;
import com.minutas.repository.TurnoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteTurnoRepository implements TurnoRepository {

    @Override
    public void save(Turno t) {
        String sql = "INSERT INTO turno (id_conjunto, id_usuario, puesto, tipo, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, t.getIdConjunto());
            pstmt.setInt(2, t.getIdUsuario());
            pstmt.setString(3, t.getPuesto());
            pstmt.setString(4, t.getTipo());
            pstmt.setString(5, t.getEstado() != null ? t.getEstado() : "ABIERTO");
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Turno t) {
        String sql = "UPDATE turno SET hora_fin = ?, estado = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, t.getHoraFin());
            pstmt.setString(2, t.getEstado());
            pstmt.setInt(3, t.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Turno> findActiveTurno(int idUsuario) {
        String sql = "SELECT * FROM turno WHERE id_usuario = ? AND estado = 'ABIERTO' ORDER BY id DESC LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Turno> findAll(int idConjunto) {
        List<Turno> lista = new ArrayList<>();
        String sql = "SELECT * FROM turno WHERE id_conjunto = ? ORDER BY id DESC";
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
    public Optional<Turno> findById(int id) {
        String sql = "SELECT * FROM turno WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Turno map(ResultSet rs) throws SQLException {
        Turno t = new Turno();
        t.setId(rs.getInt("id"));
        t.setIdConjunto(rs.getInt("id_conjunto"));
        t.setIdUsuario(rs.getInt("id_usuario"));
        t.setPuesto(rs.getString("puesto"));
        t.setTipo(rs.getString("tipo"));
        t.setHoraInicio(rs.getString("hora_inicio"));
        t.setHoraFin(rs.getString("hora_fin"));
        t.setEstado(rs.getString("estado"));
        return t;
    }
}
