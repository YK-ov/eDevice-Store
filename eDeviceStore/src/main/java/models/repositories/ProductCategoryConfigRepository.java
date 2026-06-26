package models.repositories;

import models.ProductCategoryConfig;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryConfigRepository {
    List<ProductCategoryConfig> findAll();
    Optional<ProductCategoryConfig> findByCategory(String category);
}
