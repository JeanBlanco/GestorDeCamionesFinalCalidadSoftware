package org.example;

import org.example.initializer.TruckInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TruckApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TruckApplication.class, args);
    }

    @Override
    public void run(String... args) {
        TruckInitializer.initializeDatabase();
    }
}
