package com.roerdev.arqhexagonalmodular.productprice.port.output.repository;


import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;

public interface PriceRepository {

    Price save(Price price);
    Boolean deleteById(Long id);
}