package com.roerdev.arqhexagonalmodular.productprice.rest.advice;

import com.roerdev.arqhexagonalmodular.productprice.model.exceptions.PriceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PriceControllerAdvice {

    @ExceptionHandler(PriceException.class)
    public ResponseEntity<String> handleEmptyInput(PriceException emptyInputException){
        return new ResponseEntity<>(emptyInputException.getErrorMessage(), HttpStatus.CONFLICT);
    }
}
