package com.roerdev.arqhexagonalmodular.productprice.port.output.dao;

import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PriceDao {

    List<Price> findByDateAndProductAndChain(Date date, Long product, Long chain);
    List<Price> findAll();
    Optional<Price> findById(Long id);
}
