package com.jloz.reservascanchas.controller;

import com.jloz.reservascanchas.dto.ReservaRequestDTO;
import com.jloz.reservascanchas.model.Reserva;
import com.jloz.reservascanchas.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaRepository reservaRepository){
        this.reservaRepository = reservaRepository;
    }

    @PostMapping
    public Reserva crear(@Valid @RequestBody ReservaRequestDTO dto){
        return reservaRepository.crearReserva(
                dto.getCanchaId(),
                dto.getFecha(),
                dto.getHoraInicio(),
                dto.getNombreEstudiante(),
                dto.getEmailEstudiante()
        );
    }

    @GetMapping
    public List<Reserva> listar(){
        return reservaRepository.listarReservas();
    }
}
