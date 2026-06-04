package com.pao.project.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        String path = "/com/pao/project/resources/db.properties";
        try (InputStream is = DatabaseConnection.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new RuntimeException("Fisierul db.properties nu a fost gasit la calea: " + path);
            }
            Properties props = new Properties();
            props.load(is);
            this.connection = DriverManager.getConnection(
                props.getProperty("db.url"), 
                props.getProperty("db.user", ""), 
                props.getProperty("db.password", "")
            );
        } catch (Exception e) {
            throw new SQLException("Eroare la conectarea la baza de date: " + e.getMessage(), e);
        }
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}