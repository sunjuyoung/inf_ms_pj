package com.test.catalogservice.service;

import com.test.catalogservice.entity.CatalogEntity;

import java.util.List;

public interface CatalogService {

    List<CatalogEntity> getAllCatalogs();
    CatalogEntity getCatalogByProductId(String productId);
}
