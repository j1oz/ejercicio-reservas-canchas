package com.jloz.reservascanchas.controller;

import com.jloz.reservascanchas.model.Reserva;
import com.jloz.reservascanchas.repository.ReservaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaRepository reservaRepository,){
        this.reservaRepository = reservaRepository;
    }

    @GetMapping
    public List<Reserva> listar(){
        return reservaRepository.listarReservas();
    }
}
