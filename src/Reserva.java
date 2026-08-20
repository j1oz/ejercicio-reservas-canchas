import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private static int contadorId = 0;
    private final int id;
    private int canchaId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private String nombreEstudiante;
    private String emailEstudiante;
    private EstadoReserva estado;

    public Reserva(int canchaId, LocalDate fecha, LocalTime horaInicio, String nombreEstudiante, String emailEstudiante){
        contadorId++;
        this.id = contadorId;
        this.canchaId = canchaId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.nombreEstudiante = nombreEstudiante;
        this.emailEstudiante = emailEstudiante;
        this.estado = EstadoReserva.CONFIRMADA;
    }

    public int getId(){
        return id;
    }

    public int getCanchaId() {
        return canchaId;
    }

    public void setCanchaId(int canchaId) {
        this.canchaId = canchaId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getEmailEstudiante() {
        return emailEstudiante;
    }

    public void setEmailEstudiante(String emailEstudiante) {
        this.emailEstudiante = emailEstudiante;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
