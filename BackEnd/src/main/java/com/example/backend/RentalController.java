package com.example.backend;

import com.example.backend.Entity.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
