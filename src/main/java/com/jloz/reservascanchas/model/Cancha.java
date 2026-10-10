package com.jloz.reservascanchas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cancha")
public class Cancha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private TipoCancha tipo;
    @Enumerated(EnumType.STRING)
    private EstadoCancha estado;

    public Cancha() {
    }

    public Cancha(Integer id, String nombre, TipoCancha tipo, EstadoCancha estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTipo(TipoCancha tipo) {
        this.tipo = tipo;
    }

    public TipoCancha getTipo() {
        return tipo;
    }

    public void setEstado(EstadoCancha estado) {
        this.estado = estado;
    }

    public EstadoCancha getEstado() {
        return estado;
    }
}
