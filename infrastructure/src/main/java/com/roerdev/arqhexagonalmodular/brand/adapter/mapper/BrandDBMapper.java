package com.roerdev.arqhexagonalmodular.brand.adapter.mapper;


import com.roerdev.arqhexagonalmodular.brand.adapter.entity.BrandEntity;
import com.roerdev.arqhexagonalmodular.brand.model.entity.Brand;
import com.roerdev.arqhexagonalmodular.productprice.adapter.entity.PriceEntity;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandDBMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    BrandEntity mapToBrandEntity(Brand domain);


    @InheritInverseConfiguration
    Brand mapToBrandDomain(BrandEntity stockEntity);

}
