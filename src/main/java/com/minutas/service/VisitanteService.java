package com.minutas.service;

import com.minutas.model.RegistroVisita;
import com.minutas.model.Visitante;
import com.minutas.repository.RegistroVisitaRepository;
import com.minutas.repository.VisitanteRepository;
import com.minutas.repository.local.SqliteRegistroVisitaRepository;
import com.minutas.repository.local.SqliteVisitanteRepository;

import java.util.Optional;

public class VisitanteService {
    private final VisitanteRepository visitanteRepository;
    private final RegistroVisitaRepository registroVisitaRepository;

    public VisitanteService() {
        this.visitanteRepository = new SqliteVisitanteRepository();
        this.registroVisitaRepository = new SqliteRegistroVisitaRepository();
    }

    public VisitanteService(VisitanteRepository vr, RegistroVisitaRepository rvr) {
        this.visitanteRepository = vr;
        this.registroVisitaRepository = rvr;
    }

    public void registrarVisita(Visitante visitante, RegistroVisita visita) throws SecurityException {
        // Verificar lista negra
        Optional<Visitante> existente = visitanteRepository.findByDocumento(visitante.getIdConjunto(), visitante.getDocumento());
        Visitante v;
        if (existente.isPresent()) {
            v = existente.get();
            if (v.getListaNegra() == 1) {
                throw new SecurityException("¡ALARMA! El visitante " + v.getNombre() + " se encuentra en LISTA NEGRA. Ingreso denegado.");
            }
        } else {
            visitanteRepository.save(visitante);
            v = visitanteRepository.findByDocumento(visitante.getIdConjunto(), visitante.getDocumento()).orElse(visitante);
        }

        visita.setIdVisitante(v.getId());
        visita.setIdConjunto(visitante.getIdConjunto());
        registroVisitaRepository.save(visita);
    }

    public boolean verificarListaNegra(int idConjunto, String documento) {
        Optional<Visitante> v = visitanteRepository.findByDocumento(idConjunto, documento);
        return v.map(visitante -> visitante.getListaNegra() == 1).orElse(false);
    }
}
