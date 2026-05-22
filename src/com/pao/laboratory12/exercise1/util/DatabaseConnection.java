package com.pao.laboratory12.exercise1.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException, IOException {
        Properties props = new Properties();
        
        try (FileInputStream fis = new FileInputStream("src/com/pao/laboratory12/resources/db.properties")) {
            props.load(fis);
        }

        String url = props.getProperty("db.url");
        this.connection = DriverManager.getConnection(url);

        try (var stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException ignored) {}
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException, IOException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}