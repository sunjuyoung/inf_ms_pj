package com.test.catalogservice.repository;

import com.test.catalogservice.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity,Long> {

    CatalogEntity findByProductId(String productId);
}
