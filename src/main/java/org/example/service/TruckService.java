package org.example.service;


import org.example.domain.Truck;
import org.example.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    @Autowired
    private final TruckRepository truckRepository;

    @Autowired
    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public List<Truck> searchTrucksByPlate(String plate) {
        return truckRepository.searchByPlate(plate);
    }

    public void saveTruck(Truck truck) {
        truckRepository.save(truck);
    }

    public void markTruckAsOnRoute(Long id) {
        Truck truck = truckRepository.findById(id).orElseThrow(() -> new RuntimeException("Truck not found"));
        truck.setStatus("En ruta");
        truckRepository.update(truck);
    }

    public void deleteTruck(Long id) {
        truckRepository.deleteById(id);
    }
}
