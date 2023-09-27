package com.example.backend;

import com.example.backend.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Rental getCheapestRental() {
        Pageable topOne = PageRequest.of(0, 1);
        List<Rental> rentals = rentalRepository.findAllByOrderByPriceAsc(topOne);
        if (rentals.isEmpty()) {
            return null;
        }
        return rentals.get(0);
    }
    public void insertRental(String link, String suite, int price, String location) {
        Rental rental = new Rental();
        rental.setLink(link);
        rental.setSuite(suite);
        rental.setPrice(price);
        rental.setLocation(location);

        rentalRepository.save(rental);
        System.out.println("Insert success");
    }

}
