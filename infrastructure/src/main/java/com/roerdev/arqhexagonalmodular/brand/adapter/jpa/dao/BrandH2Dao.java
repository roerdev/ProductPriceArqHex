package com.roerdev.arqhexagonalmodular.brand.adapter.jpa.dao;

import com.roerdev.arqhexagonalmodular.brand.adapter.jpa.BrandAdapterRepository;
import com.roerdev.arqhexagonalmodular.brand.adapter.mapper.BrandDBMapper;
import com.roerdev.arqhexagonalmodular.brand.model.entity.Brand;
import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.BrandDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandH2Dao implements BrandDao {

    private final BrandAdapterRepository brandAdapterRepository;
    private final BrandDBMapper brandDBMapper;

    public BrandH2Dao(BrandAdapterRepository brandAdapterRepository, BrandDBMapper brandDBMapper) {
        this.brandAdapterRepository = brandAdapterRepository;
        this.brandDBMapper = brandDBMapper;
    }

    @Override
    public Optional<Brand> findByDescription(String chain) {
        return this.brandAdapterRepository.findByDescription(chain)
                .map(brandDBMapper::mapToBrandDomain)
                .or(Optional::empty);
    }
}
