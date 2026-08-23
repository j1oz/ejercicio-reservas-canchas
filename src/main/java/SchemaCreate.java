import java.sql.SQLException;
import java.sql.Statement;

public class SchemaCreate {
    private final ConexionSQLite conexion;

    public SchemaCreate(ConexionSQLite conexion) {
        this.conexion = conexion;
    }

    public void crearTablas() {
        String cancha = """
                CREATE TABLE IF NOT EXISTS cancha (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(20),
                tipo TEXT,
                estado TEXT)
                """;
        String reserva = """
                CREATE TABLE IF NOT EXISTS reserva (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cancha_id INTEGER,
                fecha DATE,
                hora TIME,
                nombreestudiante VARCHAR(20),
                emailestudiante TEXT,
                estado TEXT,
                FOREIGN KEY (cancha_id) REFERENCES cancha(id)
                        ON DELETE RESTRICT
                        ON UPDATE RESTRICT)
                """;


        try (Statement stmt = conexion.getConexion().createStatement()) {
            stmt.execute(cancha);
            stmt.execute(reserva);
        } catch (SQLException e) {
            throw new RuntimeException("Error creando tablas", e);
        }
    }
}
