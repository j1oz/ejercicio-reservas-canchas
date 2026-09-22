package com.jloz.reservascanchas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequestDTO {
    @NotNull
    @Positive
    private Integer canchaId;
    @NotNull
    private LocalDate fecha;
    @NotNull
    private LocalTime horaInicio;
    @NotBlank
    private String nombreEstudiante;
    @NotBlank
    @Email
    private String emailEstudiante;

    public Integer getCanchaId() {
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
