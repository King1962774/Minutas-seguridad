package com.minutas.service;

import com.minutas.model.Visitante;
import com.minutas.repository.RegistroVisitaRepository;
import com.minutas.repository.VisitanteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VisitanteServiceTest {

    @Test
    void testVisitanteListaNegraGeneraExcepcion() {
        VisitanteRepository visitanteRepo = Mockito.mock(VisitanteRepository.class);
        RegistroVisitaRepository registroRepo = Mockito.mock(RegistroVisitaRepository.class);
        VisitanteService service = new VisitanteService(visitanteRepo, registroRepo);

        Visitante negro = new Visitante();
        negro.setIdConjunto(1);
        negro.setNombre("Hacker Maligno");
        negro.setDocumento("66666666");
        negro.setListaNegra(1);

        Mockito.when(visitanteRepo.findByDocumento(1, "66666666")).thenReturn(Optional.of(negro));

        SecurityException ex = assertThrows(SecurityException.class, () -> {
            service.registrarVisita(negro, new com.minutas.model.RegistroVisita());
        });

        assertTrue(ex.getMessage().contains("LISTA NEGRA"));
    }
}
