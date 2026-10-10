package com.jloz.reservascanchas.repository;

import com.jloz.reservascanchas.exception.RecursoNoEncontradoException;
import com.jloz.reservascanchas.model.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanchaRepository extends JpaRepository<Cancha, Integer> {

    default Cancha obtenerCanchaPorId(Integer id) {
        return findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La cancha con ID " + id + " no existe."));
    }
}