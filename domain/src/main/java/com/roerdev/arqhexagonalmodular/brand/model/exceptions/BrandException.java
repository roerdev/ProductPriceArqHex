package com.roerdev.arqhexagonalmodular.brand.model.exceptions;

public class BrandException extends RuntimeException{

    private String errorMessage;

    public BrandException() {
        super();
    }

    public BrandException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
