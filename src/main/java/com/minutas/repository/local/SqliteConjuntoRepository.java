package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.Conjunto;
import com.minutas.repository.ConjuntoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteConjuntoRepository implements ConjuntoRepository {
    @Override
    public List<Conjunto> findAll() {
        List<Conjunto> lista = new ArrayList<>();
        String sql = "SELECT * FROM conjunto";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Conjunto c = new Conjunto();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setNit(rs.getString("nit"));
                c.setDireccion(rs.getString("direccion"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void save(Conjunto c) {
        String sql = "INSERT INTO conjunto (nombre, nit, direccion, telefono, tipo_conjunto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getNombre());
            pstmt.setString(2, c.getNit());
            pstmt.setString(3, c.getDireccion());
            pstmt.setString(4, c.getTelefono());
            pstmt.setString(5, "TORRES");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
