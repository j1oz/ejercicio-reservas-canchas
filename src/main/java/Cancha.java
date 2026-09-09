public class Cancha {
    private final int id;
    private String nombre;
    private TipoCancha tipo;
    private EstadoCancha estado;

    public Cancha(int id, String nombre, TipoCancha tipo, EstadoCancha estado) {
        this.id = id;
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
