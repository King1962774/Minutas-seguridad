package com.minutas.repository;

// TODO-BACKEND: En el futuro, esta interfaz tendrá una implementación ApiVisitanteRepository que consuma REST, sin modificar la capa service.

import com.minutas.model.Visitante;
import java.util.List;
import java.util.Optional;

public interface VisitanteRepository {
    List<Visitante> findAll(int idConjunto);
    Optional<Visitante> findById(int id);
    Optional<Visitante> findByDocumento(int idConjunto, String documento);
    void save(Visitante visitante);
    void update(Visitante visitante);
    void delete(int id);
}
