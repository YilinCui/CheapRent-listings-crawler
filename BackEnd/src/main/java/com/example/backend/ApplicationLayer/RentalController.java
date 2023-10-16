package com.example.backend.ApplicationLayer;

import com.example.backend.Entity.Rental;
import com.example.backend.ServiceLayer.DatabaseService;
import com.example.backend.ServiceLayer.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

//@CrossOrigin(origins = "http://${REACT_HOST:192.168.20.116}:3000")
@CrossOrigin(origins = "*")
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

    @GetMapping("/studio")
    public List<Rental> getStudioDesc() {
        return rentalService.getStudioDesc();
    }
    @GetMapping("/start")
    public void startCrawling(){
        databaseService.dropTable();
        databaseService.createTable();
        rentalService.startCrawling("https://www.apartments.com/");
    }

    @RequestMapping("/error")
    public ResponseEntity<Rental> handleError() {
        Rental rental = new Rental();
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

}
