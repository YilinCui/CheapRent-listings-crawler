//package com.example.backend.ApplicationLayer;
//
//import com.example.backend.Entity.Rental;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//
//@ControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//    protected ResponseEntity<Object> ResponseEntityExceptionHandler(
//            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//        Rental rental = new Rental();
//
//        return new ResponseEntity<>(rental, HttpStatus.OK);
//    }
//}
