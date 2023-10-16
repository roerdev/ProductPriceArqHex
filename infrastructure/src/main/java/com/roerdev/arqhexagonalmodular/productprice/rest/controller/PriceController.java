package com.roerdev.arqhexagonalmodular.productprice.rest.controller;

import com.roerdev.arqhexagonalmodular.productprice.model.dto.PriceDto;
import com.roerdev.arqhexagonalmodular.productprice.port.input.PriceUseCase;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/prices")
@Tag(name = "Product Price", description = "Product price operations")
@Slf4j
public class PriceController {


    private final PriceUseCase priceUseCase;

    public PriceController(PriceUseCase priceUseCase) {
        this.priceUseCase = priceUseCase;
    }

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error") })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PriceDto> getAllPrices() {
        return priceUseCase.getAllPrices();
    }

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error") })
    @GetMapping("/{id}")
    public ResponseEntity<PriceDto> getPriceById(@PathVariable Long id) {
        Optional<PriceDto> price = priceUseCase.getPriceById(id);
        return price.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error") })
    @GetMapping("/{date}/{product}/{chain}")
    public ResponseEntity<PriceDto> getPriceByDateAndProductAndChain(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date,
                                                                     @PathVariable Long product,
                                                                     @PathVariable String chain) {
        Optional<PriceDto> price = priceUseCase.getPriceByDateAndProductAndChain(date, product, chain);
        return price.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error") })
    @PostMapping
    public ResponseEntity<PriceDto> createPrice(@RequestBody PriceDto price) {
        PriceDto createdPrice = priceUseCase.createPrice(price);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrice);
    }

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error") })
    @PutMapping("/{id}")
    public ResponseEntity<PriceDto> updatePrice(@PathVariable Long id, @RequestBody PriceDto updatedPrice) {
        PriceDto updated = priceUseCase.updatePrice(id, updatedPrice);
        return updated != null ?
                ResponseEntity.ok().body(updated) :
                ResponseEntity.notFound().build();
    }

    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Error") })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceUseCase.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}

