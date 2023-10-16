package com.roerdev.arqhexagonalmodular.productprice.port.output.dao;

import com.roerdev.arqhexagonalmodular.brand.model.entity.Brand;

import java.util.Optional;

public interface BrandDao {

    Optional<Brand> findByDescription(String chain);
}
