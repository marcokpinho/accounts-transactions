package com.marco.transactions.exceptions;

import java.util.List;

public class ErrorResponseDTO {
    private String message;
    private List<String> validations;
    public ErrorResponseDTO(String message) {
        this.message = message;
    }

    public ErrorResponseDTO(String message, List<String> validations) {
        this.message = message;
        this.validations = validations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getValidations() {
        return validations;
    }

    public void setValidations(List<String> validations) {
        this.validations = validations;
    }
}
