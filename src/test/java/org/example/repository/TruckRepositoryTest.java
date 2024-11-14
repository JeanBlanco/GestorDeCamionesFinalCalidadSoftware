package org.example.repository;

import org.example.domain.Truck;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TruckRepositoryTest {

    private TruckRepository truckRepository;

    @BeforeEach
    void setUp() throws SQLException {
        truckRepository = new TruckRepository();
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:truckdb");
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS trucks");
            statement.execute("CREATE TABLE trucks (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "plate VARCHAR(255), " +
                    "driver VARCHAR(255), " +
                    "status VARCHAR(255))");
        }
    }

    @Test
    void save_ShouldAddTruckToDatabase() throws SQLException {
        Truck truck = new Truck(null, "ABC123", "John Doe", "Available");
        truckRepository.save(truck);

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:truckdb");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM trucks WHERE plate = ?")) {
            statement.setString(1, "ABC123");
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals("ABC123", resultSet.getString("plate"));
            assertEquals("John Doe", resultSet.getString("driver"));
            assertEquals("Available", resultSet.getString("status"));
        }
    }

    @Test
    void findById_ShouldReturnTruck() throws SQLException {
        Truck truck = new Truck(null, "DEF456", "Jane Smith", "On route");
        truckRepository.save(truck);

        Optional<Truck> foundTruck = truckRepository.findById(1L);
        assertTrue(foundTruck.isPresent());
        assertEquals("DEF456", foundTruck.get().getPlate());
        assertEquals("Jane Smith", foundTruck.get().getDriver());
        assertEquals("On route", foundTruck.get().getStatus());
    }

    @Test
    void findAll_ShouldReturnAllTrucks() throws SQLException {
        Truck truck1 = new Truck(null, "GHI789", "James Brown", "Available");
        Truck truck2 = new Truck(null, "JKL012", "Mary Johnson", "On route");
        truckRepository.save(truck1);
        truckRepository.save(truck2);

        List<Truck> trucks = truckRepository.findAll();
        assertEquals(2, trucks.size());
    }

    @Test
    void update_ShouldModifyTruckData() throws SQLException {
        Truck truck = new Truck(null, "XYZ123", "Paul White", "Available");
        truckRepository.save(truck);

        Optional<Truck> savedTruck = truckRepository.findAll().stream()
                .filter(t -> t.getPlate().equals("XYZ123"))
                .findFirst();

        assertTrue(savedTruck.isPresent(), "El camión debería haberse guardado correctamente");
        assertNotNull(savedTruck.get().getId(), "El id del camión no debe ser null después de guardarlo");

        Truck truckToUpdate = savedTruck.get();
        truckToUpdate.setDriver("Paul Black");
        truckToUpdate.setStatus("On route");

        truckRepository.update(truckToUpdate);

        Optional<Truck> updatedTruck = truckRepository.findById(truckToUpdate.getId());
        assertTrue(updatedTruck.isPresent());
        assertEquals("Paul Black", updatedTruck.get().getDriver());
        assertEquals("On route", updatedTruck.get().getStatus());
    }


    @Test
    void deleteById_ShouldRemoveTruck() throws SQLException {
        Truck truck = new Truck(null, "MNO345", "Alice Green", "Available");
        truckRepository.save(truck);

        truckRepository.deleteById(1L);

        Optional<Truck> deletedTruck = truckRepository.findById(1L);
        assertFalse(deletedTruck.isPresent());
    }

    @Test
    void searchByPlate_ShouldReturnMatchingTrucks() throws SQLException {
        Truck truck1 = new Truck(null, "MNO345", "Alice Green", "Available");
        Truck truck2 = new Truck(null, "ABC123", "John Doe", "On route");
        truckRepository.save(truck1);
        truckRepository.save(truck2);

        List<Truck> trucks = truckRepository.searchByPlate("MNO");
        assertEquals(1, trucks.size());
        assertEquals("MNO345", trucks.get(0).getPlate());
    }





}

