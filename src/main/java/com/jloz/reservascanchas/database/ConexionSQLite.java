package com.jloz.reservascanchas.database;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConexionSQLite {

    private final DataSource dataSource;

    public ConexionSQLite(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConexion() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener una conexión del pool de la base de datos", e);
        }
    }
}
