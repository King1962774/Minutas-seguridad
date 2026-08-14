package com.minutas.repository;

import com.minutas.model.Conjunto;
import java.util.List;

public interface ConjuntoRepository {
    List<Conjunto> findAll();
    void save(Conjunto conjunto);
}
