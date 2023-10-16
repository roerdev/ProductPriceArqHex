package com.roerdev.arqhexagonalmodular.productprice.adapter.mapper;


import com.roerdev.arqhexagonalmodular.brand.adapter.entity.BrandEntity;
import com.roerdev.arqhexagonalmodular.brand.model.entity.Brand;
import com.roerdev.arqhexagonalmodular.productprice.adapter.entity.PriceEntity;
import com.roerdev.arqhexagonalmodular.productprice.model.entity.Price;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceDBMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "priceList", target = "priceList")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "curr", target = "curr")
    PriceEntity mapToPriceEntity(Price domain);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    BrandEntity mapToBrandEntity(Brand domain);

    @InheritInverseConfiguration
    Price mapToPriceDomain(PriceEntity entity);

    @InheritInverseConfiguration
    Brand mapToBrandDomain(BrandEntity stockEntity);

}
