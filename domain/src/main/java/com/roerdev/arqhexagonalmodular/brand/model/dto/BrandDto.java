package com.roerdev.arqhexagonalmodular.brand.model.dto;

import com.roerdev.arqhexagonalmodular.brand.model.entity.Brand;
import com.roerdev.arqhexagonalmodular.productprice.model.dto.PriceDto;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto implements Serializable{

    private Long id;

    private String description;

    BrandDto brandToBrandDto(Brand brand){
        return BrandDto.builder().
                id(brand.getId()).
                description(brand.getDescription())
                .build();
    }
}
