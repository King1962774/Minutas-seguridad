package com.minutas.service;

import com.minutas.model.Incidente;
import com.minutas.repository.IncidenteRepository;
import com.minutas.repository.local.SqliteIncidenteRepository;

import java.util.List;

public class IncidenteService {
    private final IncidenteRepository repository;

    public IncidenteService() {
        this.repository = new SqliteIncidenteRepository();
    }

    public void registrarPanico(int idConjunto, int idTurno, int idUsuario, String descripcion) {
        Incidente inc = new Incidente();
        inc.setIdConjunto(idConjunto);
        inc.setIdTurno(idTurno);
        inc.setIdUsuario(idUsuario);
        inc.setTipo("PANICO");
        inc.setDescripcion(descripcion);
        inc.setAtendido(0);
        repository.save(inc);
    }

    public List<Incidente> obtenerIncidentes(int idConjunto) {
        return repository.findAll(idConjunto);
    }
}
