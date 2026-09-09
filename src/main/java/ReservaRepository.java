import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservaRepository {
    private final Map<Integer, Reserva> mapReservas = new HashMap<>();
    private final CanchaRepository canchaRepository;
    ConexionSQLite conexion;

    public ReservaRepository(CanchaRepository canchaRepository, ConexionSQLite conexion) {
        this.canchaRepository = canchaRepository;
        this.conexion = conexion;
    }

    private void validarExistenciaYEstadoCancha(Cancha cancha) {
        if (cancha.getEstado() != EstadoCancha.ACTIVA) {
            throw new ReglaNegocioException("La cancha no esta disponible");
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
        String sql = "SELECT COUNT(*) FROM reserva WHERE emailestudiante = ? AND estado = ? AND id != ?";
        try (PreparedStatement statement = conexion.getConexion().prepareStatement(sql)){
            statement.setString(1, email);
            statement.setString(2, EstadoReserva.CONFIRMADA.name());
            statement.setInt(3, idReservaExcluir);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    int contador = resultado.getInt(1);
                    if (contador >= 2) {
                        throw new ReglaNegocioException("El usuario no puede tener más de 2 reservas.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } // R4

    private void validarSolapamiento(int canchaId, LocalDateTime fechaHora, int idReservaExcluir) {
        String fechaSql = fechaHora.toLocalDate().toString();
        String sql = "SELECT fecha, hora FROM reserva WHERE cancha_id = ? AND fecha = ? AND estado = ? AND id != ?";
        try (PreparedStatement stmt = conexion.getConexion().prepareStatement(sql)){
            stmt.setInt(1, canchaId);
            stmt.setString(2, fechaSql);
            stmt.setString(3, EstadoReserva.CONFIRMADA.name());
            stmt.setInt(4, idReservaExcluir);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    LocalTime horaRs = LocalTime.parse(rs.getString("hora"), DateTimeFormatter.ofPattern("HH:mm"));
                    var fechaHoraReservada = LocalDateTime.of(LocalDate.parse(fechaSql), horaRs);
                    if (fechaHora.isBefore(fechaHoraReservada.plusHours(1)) &&
                            fechaHora.plusHours(1).isAfter(fechaHoraReservada)) {
                        throw new ReglaNegocioException("La hora que usted solicita ya ha sido reservada.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// R1

        public Reserva crearReserva(int canchaId, LocalDate fecha, LocalTime horaInicio,
                             String nombreEstudiante, String emailEstudiante) {
        Cancha cancha = canchaRepository.obtenerCanchaPorId(canchaId);
        LocalDateTime fechaHora = LocalDateTime.of(fecha, horaInicio);
        LocalDateTime horaActual = LocalDateTime.now();
        String fechaString = fecha.toString();
        String horaString = horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"));

        //r6
        validarExistenciaYEstadoCancha(cancha);
        //r3 y r5
        validarAnticipacion(fechaHora, horaActual);
        //r4
        validarLimiteEstudiante(emailEstudiante, -1);
        //r1
        validarSolapamiento(canchaId, fechaHora, -1);

        String sql = "INSERT INTO reserva (cancha_id, fecha, hora, nombreestudiante, emailestudiante, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, canchaId);
            statement.setString(2, fechaString);
            statement.setString(3, horaString);
            statement.setString(4, nombreEstudiante);
            statement.setString(5, emailEstudiante);
            statement.setString(6, EstadoReserva.CONFIRMADA.toString());
            statement.executeUpdate();
            try (ResultSet generado = statement.getGeneratedKeys()) {
                if (generado.next()) {
                    int id = generado.getInt(1);
                    return new Reserva(id, canchaId, fecha, horaInicio, nombreEstudiante, emailEstudiante);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la reserva en la base de datos", e);
        }
        return null;
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(mapReservas.values());
    }

    public Reserva obtenerReservaPorId(int id) {
        Reserva reservaObtenida = mapReservas.get(id);
        if (reservaObtenida == null) {
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
