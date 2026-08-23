import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {
    private Connection conexion;

    public ConexionSQLite(String url) {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (SQLException e) {
            throw new RuntimeException("Error iniciando la conexion con la bd",e);
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error cerrando la conexion con la bd",e);
        }
    }
}
