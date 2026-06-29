package com.example.edevicestore.services.impl;

import com.example.edevicestore.models.ProductCategoryConfig;
import com.example.edevicestore.repositories.ProductCategoryConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class ProductCategoryConfigService {
    private final ProductCategoryConfigRepository configRepository;

    public ProductCategoryConfigService(ProductCategoryConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryConfig> findAllCategories(){
        return configRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductCategoryConfig getByCategory(String category){
        return configRepository.findByCategory(category).orElseThrow(() -> new IllegalArgumentException("Nieznana kategoria produktu: " + category));
    }

    @Transactional(readOnly = true)
    public boolean categoryExists(String category){
        return configRepository.findByCategory(category).isPresent();
    }


}
