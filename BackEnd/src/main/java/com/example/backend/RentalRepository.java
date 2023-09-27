package com.example.backend;

import com.example.backend.Entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query("SELECT r FROM Rental r WHERE r.suite != '1b1b' ORDER BY r.price ASC ")
    List<Rental> findAllByOrderByPriceAsc(Pageable pageable);

//    @Query(value = "SELECT * FROM Rental r WHERE r.suite != '1b1b' ORDER BY r.price DESC LIMIT 5", nativeQuery = true)
//    List<Rental> findTop5ByCustomCriteria();

}
