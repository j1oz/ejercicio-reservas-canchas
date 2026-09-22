package com.jloz.reservascanchas.dto;

import com.jloz.reservascanchas.model.EstadoCancha;
import com.jloz.reservascanchas.model.TipoCancha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CanchaRequestDTO {
    @NotBlank
    private String nombre;
    @NotNull
    private TipoCancha tipo;
    @NotNull
    private EstadoCancha estado;

    public CanchaRequestDTO (String nombre, TipoCancha tipo, EstadoCancha estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoCancha getTipo() {
        return tipo;
    }

    public EstadoCancha getEstado() {
        return estado;
    }
}
