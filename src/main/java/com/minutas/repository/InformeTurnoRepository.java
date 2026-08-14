package com.minutas.repository;

// TODO-BACKEND: Implementación futura ApiInformeTurnoRepository

import com.minutas.model.InformeTurno;
import java.util.Optional;

public interface InformeTurnoRepository {
    void save(InformeTurno informe);
    Optional<InformeTurno> findByTurno(int idTurno);
}
