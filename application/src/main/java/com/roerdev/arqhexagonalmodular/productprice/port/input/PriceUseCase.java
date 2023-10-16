package com.roerdev.arqhexagonalmodular.productprice.port.input;

import com.roerdev.arqhexagonalmodular.productprice.model.dto.PriceDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PriceUseCase {
    List<PriceDto> getAllPrices();

    Optional<PriceDto> getPriceById(Long id);

    Optional<PriceDto> getPriceByDateAndProductAndChain(Date date, Long product, String chain);

    PriceDto createPrice(PriceDto price);

    PriceDto updatePrice(Long id, PriceDto updatedPrice);

    void deletePrice(Long id);

}

