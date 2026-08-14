package com.minutas.repository;

// TODO-BACKEND: Implementación futura ApiIncidenteRepository

import com.minutas.model.Incidente;
import java.util.List;

public interface IncidenteRepository {
    void save(Incidente incidente);
    List<Incidente> findAll(int idConjunto);
}
