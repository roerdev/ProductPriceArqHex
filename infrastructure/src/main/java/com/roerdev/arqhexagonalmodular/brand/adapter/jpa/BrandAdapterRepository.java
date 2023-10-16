package com.roerdev.arqhexagonalmodular.brand.adapter.jpa;

import com.roerdev.arqhexagonalmodular.brand.adapter.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandAdapterRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByDescription(String description);
}
