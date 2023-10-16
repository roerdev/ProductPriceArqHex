package com.roerdev.arqhexagonalmodular.productprice.adapter.jpa;

import com.roerdev.arqhexagonalmodular.productprice.adapter.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductPriceAdapterRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE :date >= p.startDate AND :date <= p.endDate " +
            "AND p.productId = :product AND p.brandId.id = :chain ORDER BY p.priority DESC")
    List<PriceEntity> findByDateAndProductAndChain(Date date, Long product, Long chain);

}
