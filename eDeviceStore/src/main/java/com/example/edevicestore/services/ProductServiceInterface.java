package com.example.edevicestore.services;

import com.example.edevicestore.models.Product;

import java.util.List;

public interface ProductServiceInterface {
    List<Product> getAllProducts();
    Product getProductById(String productId);
    Product createProduct(Product product);
    Product updateProduct(String id, Product product);
    Product deleteProduct(String id);
}
