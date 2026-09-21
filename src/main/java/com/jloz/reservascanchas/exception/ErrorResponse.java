package com.jloz.reservascanchas.exception;

/**
 * @param estado El código numérico de estado HTTP (ej. 409, 404).
 * @param mensaje El mensaje amigable atrapado en un excepción explicando el error.
 */
public record ErrorResponse(int estado, String mensaje) {
}
