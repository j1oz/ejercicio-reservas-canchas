package com.jloz.reservascanchas.controller;

import com.jloz.reservascanchas.dto.CanchaRequestDTO;
import com.jloz.reservascanchas.model.Cancha;
import com.jloz.reservascanchas.repository.CanchaRepository;
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
    public Cancha crear(@RequestBody CanchaRequestDTO dto){
        return canchaRepository.crearCancha(dto.getNombre(), dto.getTipo(), dto.getEstado());
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