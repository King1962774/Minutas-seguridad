package com.minutas.repository;

// TODO-BACKEND: Implementación futura ApiUnidadRepository

import com.minutas.model.Unidad;
import java.util.List;

public interface UnidadRepository {
    List<Unidad> findAll(int idConjunto);
    void save(Unidad unidad);
}
