package org.example.initializer;

import org.example.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TruckInitializer {

    public static void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS trucks (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "plate VARCHAR(255) NOT NULL," +
                    "driver VARCHAR(255) NOT NULL," +
                    "status VARCHAR(50) NOT NULL)";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
