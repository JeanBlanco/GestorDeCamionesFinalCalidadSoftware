package org.example.repository;

import org.example.domain.Truck;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TruckRepository {

    private final Connection connection;

    public TruckRepository() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:h2:mem:truckdb");
    }

    public TruckRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Truck truck) {
        if (existsByPlate(truck.getPlate())) {
            System.err.println("Error: Ya existe un camión con esa placa.");
            return;
        }

        String sql = "INSERT INTO trucks (plate, driver, status) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, truck.getPlate());
            statement.setString(2, truck.getDriver());
            statement.setString(3, truck.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving truck: " + e.getMessage());
        }
    }

    private boolean existsByPlate(String plate) {
        String sql = "SELECT COUNT(*) FROM trucks WHERE plate = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, plate);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if truck exists: " + e.getMessage());
        }
        return false;
    }

    public Optional<Truck> findById(Long id) {
        String sql = "SELECT * FROM trucks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Truck truck = new Truck(resultSet.getLong("id"),
                        resultSet.getString("plate"),
                        resultSet.getString("driver"),
                        resultSet.getString("status"));
                return Optional.of(truck);
            }
        } catch (SQLException e) {
            System.err.println("Error finding truck by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Truck> findAll() {
        List<Truck> trucks = new ArrayList<>();
        String sql = "SELECT * FROM trucks";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Truck truck = new Truck(resultSet.getLong("id"),
                        resultSet.getString("plate"),
                        resultSet.getString("driver"),
                        resultSet.getString("status"));
                trucks.add(truck);
            }
        } catch (SQLException e) {
            System.err.println("Error finding all trucks: " + e.getMessage());
        }
        return trucks;
    }

    public void update(Truck truck) {
        String sql = "UPDATE trucks SET plate = ?, driver = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, truck.getPlate());
            statement.setString(2, truck.getDriver());
            statement.setString(3, truck.getStatus());
            statement.setLong(4, truck.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating truck: " + e.getMessage());
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM trucks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting truck by ID: " + e.getMessage());
        }
    }

    public List<Truck> searchByPlate(String plate) {
        List<Truck> trucks = new ArrayList<>();
        String sql = "SELECT * FROM trucks WHERE LOWER(plate) LIKE LOWER(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + plate + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Truck truck = new Truck(resultSet.getLong("id"),
                        resultSet.getString("plate"),
                        resultSet.getString("driver"),
                        resultSet.getString("status"));
                trucks.add(truck);
            }
        } catch (SQLException e) {
            System.err.println("Error finding trucks by plate: " + e.getMessage());
        }
        return trucks;
    }
}

