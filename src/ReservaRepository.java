import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservaRepository {
    private final Map<Integer, Reserva> mapReservas = new HashMap<>();
    private final CanchaRepository canchaRepository;

    public ReservaRepository(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }

    private void validarExistenciaYEstadoCancha(Cancha cancha) {
        if (cancha == null) {
            throw new RecursoNoEncontradoException("La cancha no existe.");
        }
        if (cancha.getEstado() == EstadoCancha.MANTENIMIENTO) {
            throw new ReglaNegocioException("La cancha está en mantenimiento");
        }
    } // R6 + existencia

    private void validarAnticipacion(LocalDateTime fechaHora, LocalDateTime ahora) {
        if (ChronoUnit.HOURS.between(ahora, fechaHora) < 2) {
            throw new ReglaNegocioException("""
                    La reserva debe realizarse con al menos 2 horas de anticipación
                    y no puede ser para una fecha y hora anteriores a la actual.""");
        }
    } // R3+R5

    private void validarLimiteEstudiante(String email, int idReservaExcluir) {
        var contador = 0;
        for (Reserva reservaEnMapa : mapReservas.values()) {
            if (reservaEnMapa.getId() != idReservaExcluir) {
                if (reservaEnMapa.getEmailEstudiante().equalsIgnoreCase(email) &&
                        reservaEnMapa.getEstado() == EstadoReserva.CONFIRMADA) {
                    contador++;
                }
            }
        }
        if (contador >= 2) {
            throw new ReglaNegocioException("El usuario no puede tener más de 2 reservas.");
        }
    } // R4

    private void validarSolapamiento(int canchaId, LocalDateTime fechaHora, int idReservaExcluir) {
        for (Reserva reservaEnMapa : mapReservas.values()) {
            if (reservaEnMapa.getCanchaId() == canchaId &&
                    reservaEnMapa.getEstado() == EstadoReserva.CONFIRMADA) {
                var fechaHoraReservada = LocalDateTime.of(reservaEnMapa.getFecha(), reservaEnMapa.getHoraInicio());
                if (reservaEnMapa.getId() != idReservaExcluir) {
                    if (fechaHora.isBefore(fechaHoraReservada.plusHours(1)) &&
                            fechaHora.plusHours(1).isAfter(fechaHoraReservada)) {
                        throw new ReglaNegocioException("La hora que usted solicita ya ha sido reservada.");
                    }
                }
            }
        }
    } // R1

    public void crearReserva(int canchaId, LocalDate fecha, LocalTime horaInicio,
                             String nombreEstudiante, String emailEstudiante) {
        Cancha cancha = canchaRepository.obtenerCanchaPorId(canchaId);
        LocalDateTime fechaHora = LocalDateTime.of(fecha, horaInicio);
        LocalDateTime horaActual = LocalDateTime.now();
        //r6
        validarExistenciaYEstadoCancha(cancha);
        //r3 y r5
        validarAnticipacion(fechaHora, horaActual);
        //r4
        validarLimiteEstudiante(emailEstudiante, -1);
        //r1
        validarSolapamiento(canchaId, fechaHora, -1);

        Reserva reserva = new Reserva(canchaId, fecha, horaInicio, nombreEstudiante, emailEstudiante);
        mapReservas.put(reserva.getId(), reserva);
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(mapReservas.values());
    }

    public Reserva obtenerReservaPorId(int id) {
        Reserva reservaObtenida = mapReservas.get(id);
        if (reservaObtenida== null) {
            throw new RecursoNoEncontradoException("La reserva no existe");
        }
        return mapReservas.get(id);
    }

    public Reserva cancelarReserva(int id) {
        Reserva reservaCancelada = obtenerReservaPorId(id);
        if (reservaCancelada.getEstado() == EstadoReserva.CANCELADA ||
                reservaCancelada.getEstado() == EstadoReserva.COMPLETADA) {
            throw new ReglaNegocioException("La reserva ya ha sido cancelada o completada");
        }
        reservaCancelada.setEstado(EstadoReserva.CANCELADA);
        return reservaCancelada;
    }

    public Reserva actualizarReserva(int id, String canchaIdStr, String fechaStr, String horaInicioStr,
                                     String nombreEstudiante, String emailEstudiante) {
        Reserva reservaActualizada = obtenerReservaPorId(id);
        if (reservaActualizada.getEstado() == EstadoReserva.CANCELADA ||
                reservaActualizada.getEstado() == EstadoReserva.COMPLETADA) {
            throw new ReglaNegocioException("La reserva ya ha sido cancelada o completada");
        }

        if (canchaIdStr.isBlank()) {
            canchaIdStr = String.valueOf(reservaActualizada.getCanchaId());
        }
        if (fechaStr.isBlank()) {
            fechaStr = reservaActualizada.getFecha().toString();
        }
        if (horaInicioStr.isBlank()) {
            horaInicioStr = reservaActualizada.getHoraInicio().toString();
        }
        if (nombreEstudiante.isBlank()) {
            nombreEstudiante = reservaActualizada.getNombreEstudiante();
        }
        if (emailEstudiante.isBlank()) {
            emailEstudiante = reservaActualizada.getEmailEstudiante();
        }

        int canchaIdInt = Integer.parseInt(canchaIdStr);
        LocalDate fecha = LocalDate.parse(fechaStr);
        LocalTime horaInicio = LocalTime.parse(horaInicioStr);
        LocalDateTime fechaHora = LocalDateTime.of(fecha, horaInicio);
        LocalDateTime horaActual = LocalDateTime.now();

        //r6
        validarExistenciaYEstadoCancha(canchaRepository.obtenerCanchaPorId(canchaIdInt));
        //r3 y r5
        validarAnticipacion(fechaHora, horaActual);
        //r4
        validarLimiteEstudiante(emailEstudiante, id);
        //r1
        validarSolapamiento(canchaIdInt, fechaHora, id);

        reservaActualizada.setCanchaId(canchaIdInt);
        reservaActualizada.setFecha(fecha);
        reservaActualizada.setHoraInicio(horaInicio);
        reservaActualizada.setNombreEstudiante(nombreEstudiante);
        reservaActualizada.setEmailEstudiante(emailEstudiante);
        return reservaActualizada;
    }
}
