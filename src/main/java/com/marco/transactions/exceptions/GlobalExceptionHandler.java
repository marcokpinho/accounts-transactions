package com.marco.transactions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<ErrorResponseDTO> genericExpceptionHandler(Exception ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    private ResponseEntity<ErrorResponseDTO> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> notFoundExceptionHandler(NotFoundException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponseDTO> buildResponseEntity(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(new ErrorResponseDTO(message));
    }
}
