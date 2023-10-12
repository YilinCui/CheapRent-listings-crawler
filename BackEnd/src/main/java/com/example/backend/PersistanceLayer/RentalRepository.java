package com.example.backend.PersistanceLayer;

import com.example.backend.Entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query("SELECT r FROM Rental r ORDER BY r.price ASC ")
    List<Rental> findAllByOrderByPriceAsc(Pageable pageable);

    @Query(value = "select * from Rental where suite = 'studio' order by price desc", nativeQuery = true)
    List<Rental> findStudioDesc();

    List<Rental> findAllByOrderByPriceAsc();

    List<Rental> findAllByOrderByPriceDesc();

}
