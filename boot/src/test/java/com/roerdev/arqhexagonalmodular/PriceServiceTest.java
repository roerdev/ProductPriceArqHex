package com.roerdev.arqhexagonalmodular;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.roerdev.arqhexagonalmodular.brand.model.dto.BrandDto;
import com.roerdev.arqhexagonalmodular.brand.model.entity.Brand;
import com.roerdev.arqhexagonalmodular.productprice.model.dto.PriceDto;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;
import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.BrandDao;
import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.PriceDao;
import com.roerdev.arqhexagonalmodular.productprice.port.output.repository.PriceRepository;
import com.roerdev.arqhexagonalmodular.productprice.services.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PriceServiceTest {

    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceRepository priceRepository;
    @Mock
    private PriceDao priceDao;
    @Mock
    private BrandDao brandDao;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void testGetAllPrices() {
        List<Price> prices = List.of(Price.builder().id(1L).brandId(new Brand()).build());
        when(priceDao.findAll()).thenReturn(prices);

        List<PriceDto> result = priceService.getAllPrices();

        assertEquals(prices.size(), result.size());
        assertEquals(prices.get(0).getId(), result.get(0).getId());
    }

    @Test
    public void testGetPriceById() {
        Long priceId = 1L;
        when(priceDao.findById(priceId)).thenReturn(Optional.of(Price.builder().id(priceId).brandId(new Brand()).build()));

        Optional<PriceDto> result = priceService.getPriceById(priceId);
        var resultDTO = result.orElse(null) == null ? null : result.get().getId();
        assertEquals(1L, resultDTO);
    }

    @Test
    public void testCreatePrice() {
        PriceDto priceToCreate = PriceDto.builder().id(1L).brandId(new BrandDto()).build();
        Price priceCreate = Price.builder().id(1L).brandId(new Brand()).build();
        when(priceRepository.save(priceCreate)).thenReturn(priceCreate);

        PriceDto result = priceService.createPrice(priceToCreate);

        assertEquals(priceToCreate, result);
    }

    @Test
    public void testUpdatePrice() {
        Long priceId = 1L;
        Price existingPrice = new Price();
        existingPrice.setId(priceId);
        existingPrice.setBrandId(new Brand());
        when(priceDao.findById(priceId)).thenReturn(Optional.of(existingPrice));
        when(priceRepository.save(existingPrice)).thenReturn(existingPrice);

        PriceDto updatedPrice = new PriceDto();
        updatedPrice.setId(priceId);
        updatedPrice.setBrandId(new BrandDto());

        PriceDto result = priceService.updatePrice(priceId, updatedPrice);

        assertEquals(updatedPrice, result);
    }

    @Test
    public void testDeletePrice() {
        Long priceId = 1L;
        when(priceRepository.deleteById(priceId)).thenReturn(Boolean.TRUE);

        priceService.deletePrice(priceId);

        verify(priceRepository, times(1)).deleteById(priceId);
    }

    @Test
    void getPriceByDateAndProductAndChainNotChain() {

        when(brandDao.findByDescription(any())).thenReturn(Optional.empty());

        Optional<PriceDto> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        assertEquals(Optional.empty(), price);
    }

    @Test
    void getPriceByDateAndProductAndChainNotPrice() {

        when(brandDao.findByDescription(any())).thenReturn(Optional.ofNullable(Brand.builder().build()));
        when(priceDao.findByDateAndProductAndChain(any(), any(), any())).thenReturn(new ArrayList<>());

        Optional<PriceDto> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        assertEquals(Optional.empty(), price);
    }

    @Test
    void getPriceByDateAndProductAndChainWithPricePrioritary() {

        Price price1 = Price.builder().id(1L).brandId(new Brand()).priority(1).build();
        Price price2 = Price.builder().id(2L).brandId(new Brand()).priority(0).build();

        PriceDto priceDTO1 = PriceDto.builder().id(1L).brandId(new BrandDto()).priority(1).build();

        when(brandDao.findByDescription(any())).thenReturn(Optional.ofNullable(Brand.builder().build()));
        when(priceDao.findByDateAndProductAndChain(any(), any(), any())).thenReturn(List.of(price1, price2));

        Optional<PriceDto> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        assertEquals(priceDTO1, price.orElse(null));
    }

    @Test
    void getPriceByDateAndProductAndChainWithPriceNotPrioritary() throws Exception{

        URL resource1 = getClass().getClassLoader().getResource("Price1.json");
        URL resource2 = getClass().getClassLoader().getResource("Price2.json");

        Price price1 = this.objectMapper.readValue(resource1, Price.class);
        Price price2 = this.objectMapper.readValue(resource2, Price.class);

        PriceDto priceDTO1 = PriceDto.builder().id(price2.getId()).brandId(new BrandDto()).priority(price2.getPriority()).build();

        when(brandDao.findByDescription(any())).thenReturn(Optional.ofNullable(Brand.builder().build()));
        when(priceDao.findByDateAndProductAndChain(any(), any(), any())).thenReturn(List.of(price1, price2));

        Optional<PriceDto> price = priceService.getPriceByDateAndProductAndChain(new Date(), 1L, "XYZ");

        var resultDTO = price.orElse(null) == null ? null : price.get().getId();
        assertEquals(price2.getId(), resultDTO);
    }
}