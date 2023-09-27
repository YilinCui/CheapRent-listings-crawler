package com.example.backend;

import com.example.backend.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
