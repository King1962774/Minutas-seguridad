package com.minutas.repository;

// TODO-BACKEND: Implementación futura ApiRegistroVisitaRepository

import com.minutas.model.RegistroVisita;
import java.util.List;

public interface RegistroVisitaRepository {
    void save(RegistroVisita visita);
    List<RegistroVisita> findAll(int idConjunto);
    long countByTurno(int idTurno);
}
