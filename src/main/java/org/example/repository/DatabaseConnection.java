package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:h2:mem:truckdb";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
