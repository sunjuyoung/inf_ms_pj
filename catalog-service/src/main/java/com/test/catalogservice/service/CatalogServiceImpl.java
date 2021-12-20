package com.test.catalogservice.service;

import com.test.catalogservice.entity.CatalogEntity;
import com.test.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService{

    private final CatalogRepository catalogRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CatalogEntity> getAllCatalogs() {
        List<CatalogEntity> entityList = catalogRepository.findAll();
        return entityList;
    }

    @Override
    public CatalogEntity getCatalogByProductId(String productId) {
        return null;
    }
}
