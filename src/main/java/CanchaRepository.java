import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CanchaRepository {
    private final ConexionSQLite conexion;

    public CanchaRepository(ConexionSQLite conexion) {
        this.conexion = conexion;
    }

    public Cancha crearCancha(String nombre, TipoCancha tipo, EstadoCancha estado) {
        String sql = "INSERT INTO cancha (nombre, tipo, estado) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexion.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, nombre);
            statement.setString(2, tipo.name());
            statement.setString(3, estado.name());
            statement.executeUpdate();
            try (ResultSet generado = statement.getGeneratedKeys()) {
                if (generado.next()) {
                    int id = generado.getInt(1);
                    return new Cancha(id, nombre, tipo, estado);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la cancha en la base de datos", e);
        }
        return null;
    }

    public List<Cancha> listarCanchas() {
        List<Cancha> lista = new ArrayList<>();
        String query = "SELECT * FROM cancha";
        try (PreparedStatement statement = conexion.getConexion().prepareStatement(query);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String tipoString = resultado.getString("tipo");
                TipoCancha tipo = TipoCancha.valueOf(tipoString);
                String estadoString = resultado.getString("estado");
                EstadoCancha estado = EstadoCancha.valueOf(estadoString);
                Cancha cancha = new Cancha(id, nombre, tipo, estado);
                lista.add(cancha);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar la cancha de la base de datos", e);
        }
        return lista;
    }

    public Cancha obtenerCanchaPorId(int id) {
        Cancha cancha;
        String query = "SELECT * FROM cancha WHERE id = ?";
        try (PreparedStatement statement = conexion.getConexion().prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultObtenido = statement.executeQuery()) {
                if (resultObtenido.next()){
                    int idRs = resultObtenido.getInt("id");
                    String nombre = resultObtenido.getString("nombre");
                    String tipoString = resultObtenido.getString("tipo");
                    TipoCancha tipo = TipoCancha.valueOf(tipoString);
                    String estadoString = resultObtenido.getString("estado");
                    EstadoCancha estado = EstadoCancha.valueOf(estadoString);
                    cancha = new Cancha(idRs, nombre, tipo, estado);
                } else {
                    throw new RecursoNoEncontradoException("La cancha no existe");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cancha;
    }

    public Cancha eliminarCanchaPorId(int id) {
        Cancha cancha = obtenerCanchaPorId(id);
        String query = "DELETE FROM cancha WHERE id = ?";
        try (PreparedStatement statement = conexion.getConexion().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la cancha", e);
        }
        return cancha;
    }

    public Cancha actualizarCancha(int id, String nombre, TipoCancha tipo, EstadoCancha estado) {
        Cancha actualizar = obtenerCanchaPorId(id);
        String query = "UPDATE cancha SET nombre = ?, tipo = ?, estado = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.getConexion().prepareStatement(query)){
            if (nombre == null) nombre = actualizar.getNombre();
            if (tipo == null) tipo = actualizar.getTipo();
            if (estado == null) estado = actualizar.getEstado();

            actualizar.setNombre(nombre);
            actualizar.setTipo(tipo);
            actualizar.setEstado(estado);

            statement.setString(1, nombre);
            statement.setString(2, tipo.name());
            statement.setString(3, estado.name());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la cancha", e);
        }
        return actualizar;
    }
}
