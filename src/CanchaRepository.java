import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanchaRepository {
    private final Map<Integer, Cancha> canchas = new HashMap<>();

    public void crearCancha(String nombre, TipoCancha tipo, EstadoCancha estado) {
        Cancha cancha = new Cancha(nombre, tipo, estado);
        canchas.put(cancha.getId(), cancha);
    }

    public List<Cancha> listarCanchas() {
        return new ArrayList<>(canchas.values());
    }

    public Cancha obtenerCanchaPorId(int id) {
        return canchas.get(id);
    }

    public Cancha eliminarCanchaPorId(int id) {
        return canchas.remove(id);
    }

    public Cancha actualizarCancha(int id, String nombre, TipoCancha tipo, EstadoCancha estado) {
        Cancha actualizar = obtenerCanchaPorId(id);
        if (actualizar == null) return null;
        if (nombre != null) actualizar.setNombre(nombre);
        if (tipo != null) actualizar.setTipo(tipo);
        if (estado != null) actualizar.setEstado(estado);
        return actualizar;
    }
}
