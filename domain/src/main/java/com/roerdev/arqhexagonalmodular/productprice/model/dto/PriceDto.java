package com.roerdev.arqhexagonalmodular.productprice.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.roerdev.arqhexagonalmodular.brand.model.dto.BrandDto;
import com.roerdev.arqhexagonalmodular.brand.model.entity.Brand;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    private Long id;
    private BrandDto brandId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Long priceList;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String curr;

    public static PriceDto priceToPriceDto(Price price){
        return PriceDto.builder().
                id(price.getId())
                .brandId(BrandDto.builder()
                        .id(price.getBrandId().getId())
                        .description(price.getBrandId().getDescription())
                        .build())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .priceList(price.getPriceList())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .price(price.getPrice())
                .curr(price.getCurr())
                .build();
    }
    public static Price priceDtotoPrice(PriceDto price){
        return Price.builder().
                id(price.getId())
                .brandId(Brand.builder()
                        .id(price.getBrandId().getId())
                        .description(price.getBrandId().getDescription())
                        .build())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .priceList(price.getPriceList())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .price(price.getPrice())
                .curr(price.getCurr())
                .build();
    }

}

