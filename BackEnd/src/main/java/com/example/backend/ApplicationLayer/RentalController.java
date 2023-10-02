package com.example.backend.ApplicationLayer;

import com.example.backend.Entity.Rental;
import com.example.backend.ServiceLayer.DatabaseService;
import com.example.backend.ServiceLayer.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rental")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/{id}")
    public Rental getRentalById(@PathVariable Integer id) {
        return rentalService.getRentalById(id);
    }

    @GetMapping("/")
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/cheapest")
    public Rental getCheapestRental() {
        return rentalService.getCheapestRental();
    }

    @GetMapping("/asc")
    public List<Rental> getAllRentalAsc() {
        return rentalService.getAllRentalsAsc();
    }

    @GetMapping("/desc")
    public List<Rental> getAllRentalDesc() {
        return rentalService.getAllRentalsDesc();
    }

    @GetMapping("/start")
    public void startCrawling(){
        databaseService.dropTable();
        databaseService.createTable();
        rentalService.startCrawling("https://www.apartments.com/");
    }
}
