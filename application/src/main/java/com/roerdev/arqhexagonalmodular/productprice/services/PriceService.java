package com.roerdev.arqhexagonalmodular.productprice.services;


import com.roerdev.arqhexagonalmodular.productprice.port.input.PriceUseCase;
import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.BrandDao;
import com.roerdev.arqhexagonalmodular.productprice.port.output.dao.PriceDao;
import com.roerdev.arqhexagonalmodular.productprice.port.output.repository.PriceRepository;
import com.roerdev.arqhexagonalmodular.productprice.util.DatesUtil;
import com.roerdev.arqhexagonalmodular.productprice.model.dto.PriceDto;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PriceService implements PriceUseCase {

    private final PriceRepository priceRepository;
    private final PriceDao priceDao;
    private final BrandDao brandDao;

    public PriceService(PriceRepository priceRepository, PriceDao priceDao, BrandDao brandDao) {
        this.priceRepository = priceRepository;
        this.priceDao = priceDao;
        this.brandDao = brandDao;
    }

    public List<PriceDto> getAllPrices() {
        List<Price> listPrices = priceDao.findAll();
        return listPrices.stream().map(PriceDto::priceToPriceDto).collect(Collectors.toList());
    }

    public Optional<PriceDto> getPriceById(Long id) {
        var price = priceDao.findById(id);
        if (price.isPresent()) {
            return Optional.ofNullable(PriceDto.priceToPriceDto(priceDao.findById(id).get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PriceDto> getPriceByDateAndProductAndChain(Date date, Long product, String chain) {
        var brain = this.brandDao.findByDescription(chain);
        if (brain.isPresent()) {
            var price = this.priceDao.findByDateAndProductAndChain(date, product, brain.get().getId());
            if(price.isEmpty()) {
                return Optional.empty();
            }
            var listPricePriority = price.stream().filter(price1 -> price1.getPriority() == 1).collect(Collectors.toList());
            if(listPricePriority.isEmpty()){
                return Optional.ofNullable(this.getPriceToApply(price));
            } else {
                return Optional.ofNullable(this.getPriceToApply(listPricePriority));
            }
        } else {
            return Optional.empty();
        }
    }

    private PriceDto getPriceToApply(List<Price> priceList){
        if (priceList.size() > 1) {
            var listPriceOrder = DatesUtil.orderByDateDifferenceAscending(priceList);
            return PriceDto.priceToPriceDto(listPriceOrder.stream().findFirst().orElse(null));
        } else {
            return PriceDto.priceToPriceDto(priceList.get(0));
        }
    }

    public PriceDto createPrice(PriceDto priceDto) {
        var price = priceRepository.save(PriceDto.priceDtotoPrice(priceDto));
        return PriceDto.priceToPriceDto(price);
    }

    public PriceDto updatePrice(Long id, PriceDto priceDto) {
        Optional<Price> existingPrice = priceDao.findById(id);
        if (existingPrice.isPresent()) {
            priceDto.setId(id);
            var price = priceRepository.save(PriceDto.priceDtotoPrice(priceDto));
            return PriceDto.priceToPriceDto(price);
        }
        return null;
    }

    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }
}
