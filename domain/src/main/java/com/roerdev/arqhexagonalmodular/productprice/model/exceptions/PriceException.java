package com.roerdev.arqhexagonalmodular.productprice.model.exceptions;

public class PriceException extends RuntimeException{

    private String errorMessage;

    public PriceException() {
        super();
    }

    public PriceException(String errorMessage) {
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
