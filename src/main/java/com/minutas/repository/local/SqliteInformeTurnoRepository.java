package com.minutas.repository.local;

import com.minutas.config.Database;
import com.minutas.model.InformeTurno;
import com.minutas.repository.InformeTurnoRepository;

import java.sql.*;
import java.util.Optional;

public class SqliteInformeTurnoRepository implements InformeTurnoRepository {
    @Override
    public void save(InformeTurno it) {
        String sql = "INSERT INTO informe_turno (id_conjunto, id_turno, resumen_visitantes, resumen_vehiculos, resumen_paquetes, pendientes, firma_entrega, firma_recibo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, it.getIdConjunto());
            pstmt.setInt(2, it.getIdTurno());
            pstmt.setInt(3, it.getResumenVisitantes());
            pstmt.setInt(4, it.getResumenVehiculos());
            pstmt.setInt(5, it.getResumenPaquetes());
            pstmt.setString(6, it.getPendientes());
            pstmt.setString(7, it.getFirmaEntrega());
            pstmt.setString(8, it.getFirmaRecibo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<InformeTurno> findByTurno(int idTurno) {
        String sql = "SELECT * FROM informe_turno WHERE id_turno = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTurno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                InformeTurno it = new InformeTurno();
                it.setId(rs.getInt("id"));
                it.setIdConjunto(rs.getInt("id_conjunto"));
                it.setIdTurno(rs.getInt("id_turno"));
                it.setResumenVisitantes(rs.getInt("resumen_visitantes"));
                it.setResumenVehiculos(rs.getInt("resumen_vehiculos"));
                it.setResumenPaquetes(rs.getInt("resumen_paquetes"));
                it.setPendientes(rs.getString("pendientes"));
                it.setFirmaEntrega(rs.getString("firma_entrega"));
                it.setFirmaRecibo(rs.getString("firma_recibo"));
                it.setCreatedAt(rs.getString("created_at"));
                return Optional.of(it);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
