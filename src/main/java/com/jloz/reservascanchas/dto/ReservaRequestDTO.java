package com.jloz.reservascanchas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequestDTO {
    private int canchaId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private String nombreEstudiante;
    private String emailEstudiante;

    public int getCanchaId() {
        return canchaId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getEmailEstudiante() {
        return emailEstudiante;
    }
}
