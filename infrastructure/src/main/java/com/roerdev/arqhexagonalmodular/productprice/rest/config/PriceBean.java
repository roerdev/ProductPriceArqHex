package com.roerdev.arqhexagonalmodular.productprice.rest.config;

import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.BrandDao;
import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.PriceDao;
import com.roerdev.arqhexagonalmodular.productprice.port.output.repository.PriceRepository;
import com.roerdev.arqhexagonalmodular.productprice.services.PriceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceBean {

    @Bean
    public PriceService priceService(PriceRepository priceRepository, PriceDao priceDao, BrandDao brandDao){
        return new PriceService(priceRepository, priceDao, brandDao);

    }
}
