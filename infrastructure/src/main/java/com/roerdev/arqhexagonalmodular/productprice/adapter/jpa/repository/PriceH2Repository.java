package com.roerdev.arqhexagonalmodular.productprice.adapter.jpa.repository;

import com.roerdev.arqhexagonalmodular.productprice.adapter.entity.PriceEntity;
import com.roerdev.arqhexagonalmodular.productprice.adapter.jpa.ProductPriceAdapterRepository;
import com.roerdev.arqhexagonalmodular.productprice.adapter.mapper.PriceDBMapper;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;
import com.roerdev.arqhexagonalmodular.productprice.port.output.repository.PriceRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceH2Repository implements PriceRepository {

    private final ProductPriceAdapterRepository productPriceAdapterRepository;
    private final PriceDBMapper priceDBMapper;

    public PriceH2Repository(ProductPriceAdapterRepository productPriceAdapterRepository, PriceDBMapper priceDBMapper) {
        this.productPriceAdapterRepository = productPriceAdapterRepository;
        this.priceDBMapper = priceDBMapper;
    }

    @Override
    public Price save(Price price) {
        PriceEntity save = productPriceAdapterRepository.save(this.priceDBMapper.mapToPriceEntity(price));
        return this.priceDBMapper.mapToPriceDomain(save);
    }

    @Override
    public Boolean deleteById(Long id) {
        this.productPriceAdapterRepository.deleteById(id);
        return true;
    }
}
