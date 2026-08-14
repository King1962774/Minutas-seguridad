package com.minutas.service;

import com.minutas.model.InformeTurno;
import com.minutas.model.Turno;
import com.minutas.repository.InformeTurnoRepository;
import com.minutas.repository.TurnoRepository;
import com.minutas.repository.local.SqliteInformeTurnoRepository;
import com.minutas.repository.local.SqliteTurnoRepository;

import java.util.Optional;

public class TurnoService {
    private final TurnoRepository turnoRepository;
    private final InformeTurnoRepository informeTurnoRepository;

    public TurnoService() {
        this.turnoRepository = new SqliteTurnoRepository();
        this.informeTurnoRepository = new SqliteInformeTurnoRepository();
    }

    public TurnoService(TurnoRepository turnoRepository, InformeTurnoRepository informeTurnoRepository) {
        this.turnoRepository = turnoRepository;
        this.informeTurnoRepository = informeTurnoRepository;
    }

    public Turno abrirTurno(int idConjunto, int idUsuario, String puesto, String tipo) {
        Optional<Turno> activo = turnoRepository.findActiveTurno(idUsuario);
        if (activo.isPresent()) {
            return activo.get();
        }
        Turno t = new Turno();
        t.setIdConjunto(idConjunto);
        t.setIdUsuario(idUsuario);
        t.setPuesto(puesto);
        t.setTipo(tipo);
        t.setEstado("ABIERTO");
        turnoRepository.save(t);
        return t;
    }

    public boolean cerrarTurno(int idTurno, InformeTurno informe) throws IllegalArgumentException {
        // Validar informe obligatorio antes de cerrar
        if (informe == null || informe.getPendientes() == null || informe.getPendientes().trim().isEmpty()) {
            throw new IllegalArgumentException("El informe de fin de turno con pendientes es obligatorio para cerrar el turno.");
        }
        
        Optional<Turno> turnoOpt = turnoRepository.findById(idTurno);
        if (turnoOpt.isPresent()) {
            Turno t = turnoOpt.get();
            t.setEstado("CERRADO");
            t.setHoraFin(java.time.LocalDateTime.now().toString());
            turnoRepository.update(t);

            informe.setIdTurno(idTurno);
            informe.setIdConjunto(t.getIdConjunto());
            informeTurnoRepository.save(informe);
            return true;
        }
        return false;
    }

    public Optional<Turno> obtenerTurnoActivo(int idUsuario) {
        return turnoRepository.findActiveTurno(idUsuario);
    }
}
