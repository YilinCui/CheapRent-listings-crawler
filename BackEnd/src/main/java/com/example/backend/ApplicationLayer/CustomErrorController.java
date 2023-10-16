package com.example.backend.ApplicationLayer;

import com.example.backend.Entity.Rental;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Rental> handleError() {
        Rental rental = new Rental();
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }
}
