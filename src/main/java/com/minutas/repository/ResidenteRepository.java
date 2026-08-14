package com.minutas.repository;

// TODO-BACKEND: Implementación futura ApiResidenteRepository

import com.minutas.model.Residente;
import java.util.List;

public interface ResidenteRepository {
    List<Residente> findAll(int idConjunto);
    void save(Residente residente);
}
