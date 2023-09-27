package com.example.backend;

import com.example.backend.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental createOrUpdateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

}
