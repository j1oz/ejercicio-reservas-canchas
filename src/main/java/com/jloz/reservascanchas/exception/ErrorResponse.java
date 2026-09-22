package com.jloz.reservascanchas.exception;

/**
 * @param status El código numérico de estado HTTP (ej. 409, 404).
 * @param message El mensaje amigable atrapado en un excepción explicando el error.
 */
public record ErrorResponse(int status, String message) {
}
