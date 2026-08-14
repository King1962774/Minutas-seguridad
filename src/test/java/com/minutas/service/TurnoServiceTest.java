package com.minutas.service;

import com.minutas.model.InformeTurno;
import com.minutas.model.Turno;
import com.minutas.repository.InformeTurnoRepository;
import com.minutas.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TurnoServiceTest {

    @Test
    void testCerrarTurnoSinInformeLanzaExcepcion() {
        TurnoRepository turnoRepo = Mockito.mock(TurnoRepository.class);
        InformeTurnoRepository informeRepo = Mockito.mock(InformeTurnoRepository.class);
        TurnoService service = new TurnoService(turnoRepo, informeRepo);

        InformeTurno informeInvalido = new InformeTurno();
        informeInvalido.setPendientes(""); // vacío

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cerrarTurno(1, informeInvalido);
        });

        assertTrue(ex.getMessage().contains("obligatorio"));
    }

    @Test
    void testCerrarTurnoConInformeValido() {
        TurnoRepository turnoRepo = Mockito.mock(TurnoRepository.class);
        InformeTurnoRepository informeRepo = Mockito.mock(InformeTurnoRepository.class);
        TurnoService service = new TurnoService(turnoRepo, informeRepo);

        Turno mockTurno = new Turno();
        mockTurno.setId(1);
        mockTurno.setIdConjunto(1);

        Mockito.when(turnoRepo.findById(1)).thenReturn(Optional.of(mockTurno));

        InformeTurno informeValido = new InformeTurno();
        informeValido.setPendientes("Todo en orden, entrega sin novedad.");

        assertDoesNotThrow(() -> {
            boolean resultado = service.cerrarTurno(1, informeValido);
            assertTrue(resultado);
        });
    }
}
