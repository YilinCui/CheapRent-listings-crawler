package com.example.backend.ApplicationLayer;

import com.example.backend.Entity.Rental;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Rental> handleAllExceptions(Exception ex, WebRequest request) {
        Rental rental = new Rental();
        return new ResponseEntity<>(rental, HttpStatus.BAD_REQUEST);
    }
}
