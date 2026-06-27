package com.example.edevicestore.repositories.impl;

import com.google.gson.reflect.TypeToken;
import com.example.edevicestore.db.JsonFileStorage;
import com.example.edevicestore.models.ProductCategoryConfig;
import com.example.edevicestore.repositories.ProductCategoryConfigRepository;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductCategoryConfigJsonRepository implements ProductCategoryConfigRepository {
    private JsonFileStorage<ProductCategoryConfig> storage;
    private final List<ProductCategoryConfig> configs;

    public ProductCategoryConfigJsonRepository(@Value("${project.json.categories-file}") String filename){
        this.storage = new JsonFileStorage<>(filename,
                new TypeToken<List<ProductCategoryConfig>>() {}.getType());
        this.configs = new ArrayList<>(storage.load());
    }

    @Override
    public List<ProductCategoryConfig> findAll() {
        List<ProductCategoryConfig> copy = new ArrayList<>();

        for (ProductCategoryConfig config : configs) {
            copy.add(config.copy());
        }

        return copy;
    }

    @Override
    public Optional<ProductCategoryConfig> findByCategory(String category) {
        return configs.stream()
                .filter(c -> c.getCategory() != null)
                .filter(c -> c.getCategory().equalsIgnoreCase(category))
                .findFirst()
                .map(ProductCategoryConfig::copy);
    }
}
