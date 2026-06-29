package com.example.edevicestore.services.impl;

import com.example.edevicestore.models.Product;
import com.example.edevicestore.repositories.ProductRepository;
import com.example.edevicestore.services.ProductServiceInterface;
import com.example.edevicestore.services.ProductValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class ProductService implements ProductServiceInterface {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public ProductService(ProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);

        if (foundProduct.isEmpty()){
            throw new IllegalArgumentException("Produktu o id " + productId + " nie znaleziono w systemie");
        }

        return foundProduct.get();
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        productValidator.validate(product);
        productRepository.save(product);

        return product;
    }

    @Override
    @Transactional
    public Product updateProduct(String id, Product product) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isEmpty()){
            throw new IllegalArgumentException("Produktu o id " + id + " nie znaleziono w systemie");
        }

        productValidator.validate(product);

        foundProduct.get().setDescription(product.getDescription());
        foundProduct.get().setPrice(product.getPrice());
        foundProduct.get().setQuantity(product.getQuantity());
        foundProduct.get().setYear(product.getYear());
        foundProduct.get().setCategory(product.getCategory());

        return productRepository.save(foundProduct.get());
    }

    @Override
    @Transactional
    public Product deleteProduct(String id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isEmpty()){
            throw new IllegalArgumentException("Produktu o id " + id + " nie znaleziono w systemie");
        }

        productRepository.deleteById(id);

        return foundProduct.get();
    }
}
