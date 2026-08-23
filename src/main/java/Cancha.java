public class Cancha {
    private static int contadorId = 0;
    private final int id;
    private String nombre;
    private TipoCancha tipo;
    private EstadoCancha estado;

    public Cancha(String nombre, TipoCancha tipo, EstadoCancha estado) {
        contadorId++;
        this.id = contadorId;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getId() {
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
