package com.jloz.reservascanchas.controller;

import com.jloz.reservascanchas.dto.CanchaRequestDTO;
import com.jloz.reservascanchas.model.Cancha;
import com.jloz.reservascanchas.repository.CanchaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canchas")   // prefijo para TODA la clase
public class CanchaController {
    private final CanchaRepository canchaRepository;

    public CanchaController(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }

    @PostMapping
    public ResponseEntity<Cancha> crear(@Valid @RequestBody CanchaRequestDTO dto){
        Cancha cancha = canchaRepository.crearCancha(dto.getNombre(), dto.getTipo(), dto.getEstado());
        return ResponseEntity.status(HttpStatus.CREATED).body(cancha);
    }

    @GetMapping           // GET /canchas (hereda el prefijo de arriba)
    public List<Cancha> listar() {
        return canchaRepository.listarCanchas();
    }

    @GetMapping("/{id}")
    public Cancha obtener(@PathVariable int id) {
        return canchaRepository.obtenerCanchaPorId(id);
    }
}