package org.example.service;


import org.example.domain.Truck;
import org.example.repository.TruckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TruckServiceTest {

    private TruckRepository truckRepository;
    private TruckService truckService;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS trucks");

        stmt.execute("CREATE TABLE trucks (id BIGINT AUTO_INCREMENT, plate VARCHAR(255), driver VARCHAR(255), status VARCHAR(255));");

        stmt.execute("INSERT INTO trucks (plate, driver, status) VALUES ('ABC123', 'John Doe', 'Marcar en ruta')");
        stmt.execute("INSERT INTO trucks (plate, driver, status) VALUES ('DEF456', 'Jane Smith', 'En ruta')");

        truckRepository = new TruckRepository(connection);
        truckService = new TruckService(truckRepository);
    }

    @Test
    void getAllTrucks_ShouldReturnAllTrucks() {
        List<Truck> trucks = truckService.getAllTrucks();

        assertEquals(2, trucks.size());
        assertEquals("ABC123", trucks.get(0).getPlate());
    }

    @Test
    void searchTrucksByPlate_ShouldReturnMatchingTrucks() {
        List<Truck> trucks = truckService.searchTrucksByPlate("ABC");

        assertEquals(1, trucks.size());
        assertEquals("ABC123", trucks.get(0).getPlate());
    }

    @Test
    void saveTruck_ShouldSaveTruck() {
        Truck newTruck = new Truck(null, "GHI789", "James Brown", "En ruta");
        truckService.saveTruck(newTruck);

        List<Truck> trucks = truckService.getAllTrucks();
        assertEquals(3, trucks.size());
    }

    @Test
    void markTruckAsOnRoute_ShouldUpdateTruckStatus() {
        Truck truck = truckService.getAllTrucks().get(0);
        truckService.markTruckAsOnRoute(truck.getId());

        assertEquals("Marcar en ruta", truck.getStatus());
    }

    @Test
    void deleteTruck_ShouldDeleteTruck() {
        Truck truck = truckService.getAllTrucks().get(0);
        truckService.deleteTruck(truck.getId());

        List<Truck> trucks = truckService.getAllTrucks();
        assertEquals(1, trucks.size());
    }
}
