package com.minutas.service;

import com.minutas.model.NovedadTurno;
import com.minutas.repository.NovedadTurnoRepository;
import com.minutas.repository.local.SqliteNovedadTurnoRepository;

import java.util.List;

public class NovedadService {
    private final NovedadTurnoRepository repository;

    public NovedadService() {
        this.repository = new SqliteNovedadTurnoRepository();
    }

    public void agregarNovedad(int idConjunto, int idTurno, String categoria, String descripcion) {
        NovedadTurno n = new NovedadTurno();
        n.setIdConjunto(idConjunto);
        n.setIdTurno(idTurno);
        n.setCategoria(categoria);
        n.setDescripcion(descripcion);
        repository.save(n);
    }

    public List<NovedadTurno> obtenerNovedadesTurno(int idTurno) {
        return repository.findByTurno(idTurno);
    }
}
