package com.jloz.reservascanchas.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConexionSQLite {
    private Connection conexion;

    public ConexionSQLite(@Value("${ruta.bd}") String url) {
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
