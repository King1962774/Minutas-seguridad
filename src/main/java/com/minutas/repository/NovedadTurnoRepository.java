package com.minutas.repository;

// TODO-BACKEND: Implementación futura ApiNovedadTurnoRepository

import com.minutas.model.NovedadTurno;
import java.util.List;

public interface NovedadTurnoRepository {
    void save(NovedadTurno novedad);
    List<NovedadTurno> findByTurno(int idTurno);
}
