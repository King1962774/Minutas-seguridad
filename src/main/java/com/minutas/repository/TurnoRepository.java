package com.minutas.repository;

// TODO-BACKEND: Implementación futura ApiTurnoRepository

import com.minutas.model.Turno;
import java.util.List;
import java.util.Optional;

public interface TurnoRepository {
    void save(Turno turno);
    void update(Turno turno);
    Optional<Turno> findActiveTurno(int idUsuario);
    List<Turno> findAll(int idConjunto);
    Optional<Turno> findById(int id);
}
