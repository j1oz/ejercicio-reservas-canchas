package com.jloz.reservascanchas.controller;

import com.jloz.reservascanchas.dto.ReservaRequestDTO;
import com.jloz.reservascanchas.model.Reserva;
import com.jloz.reservascanchas.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.hibernate.validator.internal.constraintvalidators.bv.PatternValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaRepository reservaRepository;
    private PatternValidator patternValidator;

    public ReservaController(ReservaRepository reservaRepository){
        this.reservaRepository = reservaRepository;
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@Valid @RequestBody ReservaRequestDTO dto){
        Reserva reserva = reservaRepository.crearReserva(
                dto.getCanchaId(),
                dto.getFecha(),
                dto.getHoraInicio(),
                dto.getNombreEstudiante(),
                dto.getEmailEstudiante()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @GetMapping
    public List<Reserva> listar(){
        return reservaRepository.listarReservas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable int id){
        reservaRepository.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
