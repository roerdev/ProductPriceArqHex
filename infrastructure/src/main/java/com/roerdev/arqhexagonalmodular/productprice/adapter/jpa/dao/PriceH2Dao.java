package com.roerdev.arqhexagonalmodular.productprice.adapter.jpa.dao;

import com.roerdev.arqhexagonalmodular.productprice.adapter.entity.PriceEntity;
import com.roerdev.arqhexagonalmodular.productprice.adapter.jpa.ProductPriceAdapterRepository;
import com.roerdev.arqhexagonalmodular.productprice.adapter.mapper.PriceDBMapper;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;
import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.PriceDao;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceH2Dao implements PriceDao {

    private final ProductPriceAdapterRepository productPriceAdapterRepository;
    private final PriceDBMapper priceDBMapper;

    public PriceH2Dao(ProductPriceAdapterRepository productPriceAdapterRepository, PriceDBMapper priceDBMapper) {
        this.productPriceAdapterRepository = productPriceAdapterRepository;
        this.priceDBMapper = priceDBMapper;
    }

    @Override
    public List<Price> findByDateAndProductAndChain(Date date, Long product, Long chain) {
        List<PriceEntity> byDateAndProductAndChain = this.productPriceAdapterRepository
                .findByDateAndProductAndChain(date, product, chain);

        return byDateAndProductAndChain.stream()
                .map(priceDBMapper::mapToPriceDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Price> findAll() {
        return this.productPriceAdapterRepository.findAll().stream()
                .map(priceDBMapper::mapToPriceDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Price> findById(Long id) {
        Optional<PriceEntity> byId = this.productPriceAdapterRepository.findById(id);
        return byId.map(priceDBMapper::mapToPriceDomain).or(Optional::empty);
    }
}
