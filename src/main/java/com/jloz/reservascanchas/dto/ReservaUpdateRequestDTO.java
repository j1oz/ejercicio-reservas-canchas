package com.jloz.reservascanchas.dto;

import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaUpdateRequestDTO (
                                       @Positive Integer canchaId,
                                       LocalDate fecha,
                                       LocalTime horaInicio,
                                       String nombreEstudiante) {
}