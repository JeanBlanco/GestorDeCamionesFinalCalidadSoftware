package org.example.controller;


import org.example.domain.Truck;
import org.example.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/trucks")
public class TruckController {

    @Autowired
    private TruckService truckService;

    @GetMapping
    public List<Truck> getTrucks(@RequestParam(value = "plate", required = false) String plate) {
        if (plate != null) {
            return truckService.searchTrucksByPlate(plate);
        }
        return truckService.getAllTrucks();
    }

    @PostMapping
    public void addTruck(@RequestBody Truck truck) {
        truckService.saveTruck(truck);
    }

    @PutMapping("/{id}/onroute")
    public void markAsOnRoute(@PathVariable Long id) {
        truckService.markTruckAsOnRoute(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTruck(@PathVariable Long id) {
        truckService.deleteTruck(id);
    }
}

