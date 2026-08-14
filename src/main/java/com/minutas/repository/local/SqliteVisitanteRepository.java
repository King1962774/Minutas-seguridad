package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.Visitante;
import com.minutas.repository.VisitanteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteVisitanteRepository implements VisitanteRepository {

    @Override
    public List<Visitante> findAll(int idConjunto) {
        List<Visitante> lista = new ArrayList<>();
        String sql = "SELECT * FROM visitante WHERE id_conjunto = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idConjunto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToVisitante(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Optional<Visitante> findById(int id) {
        String sql = "SELECT * FROM visitante WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToVisitante(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Visitante> findByDocumento(int idConjunto, String documento) {
        String sql = "SELECT * FROM visitante WHERE id_conjunto = ? AND documento = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idConjunto);
            pstmt.setString(2, documento);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToVisitante(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Visitante v) {
        String sql = "INSERT INTO visitante (id_conjunto, nombre, documento, telefono, observaciones, lista_negra) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, v.getIdConjunto());
            pstmt.setString(2, v.getNombre());
            pstmt.setString(3, v.getDocumento());
            pstmt.setString(4, v.getTelefono());
            pstmt.setString(5, v.getObservaciones());
            pstmt.setInt(6, v.getListaNegra());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Visitante v) {
        String sql = "UPDATE visitante SET nombre = ?, documento = ?, telefono = ?, observaciones = ?, lista_negra = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, v.getNombre());
            pstmt.setString(2, v.getDocumento());
            pstmt.setString(3, v.getTelefono());
            pstmt.setString(4, v.getObservaciones());
            pstmt.setInt(5, v.getListaNegra());
            pstmt.setInt(6, v.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM visitante WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Visitante mapResultSetToVisitante(ResultSet rs) throws SQLException {
        Visitante v = new Visitante();
        v.setId(rs.getInt("id"));
        v.setIdConjunto(rs.getInt("id_conjunto"));
        v.setNombre(rs.getString("nombre"));
        v.setDocumento(rs.getString("documento"));
        v.setTelefono(rs.getString("telefono"));
        v.setObservaciones(rs.getString("observaciones"));
        v.setListaNegra(rs.getInt("lista_negra"));
        return v;
    }
}
