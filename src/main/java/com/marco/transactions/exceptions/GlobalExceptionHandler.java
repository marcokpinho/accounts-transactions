package com.marco.transactions.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<ErrorResponseDTO> genericExpceptionHandler(Exception ex) {
        return buildResponseEntity(new ErrorResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    private ResponseEntity<ErrorResponseDTO> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return buildResponseEntity(new ErrorResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> notFoundExceptionHandler(NotFoundException ex) {
        return buildResponseEntity(new ErrorResponseDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseDTO> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> validationErrors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return buildResponseEntity(new ErrorResponseDTO("Problema ao processar requisição", validationErrors), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseDTO> buildResponseEntity(ErrorResponseDTO body, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(body);
    }
}
