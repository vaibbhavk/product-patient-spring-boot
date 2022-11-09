package com.Product_patient.Product_patient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(com.Product_patient.Product_patient.exception.ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(com.Product_patient.Product_patient.exception.ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.Product_patient.Product_patient.exception.BadRequestException.class)
    public ResponseEntity<?> badRequestException(com.Product_patient.Product_patient.exception.BadRequestException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
