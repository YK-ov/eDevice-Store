package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.Product;
import com.example.edevicestore.repositories.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")

public class ProductRepositoryJpaAdapter implements ProductRepository {
    private final ProductJpaRepository delegate;

    public ProductRepositoryJpaAdapter(ProductJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Product> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Product> findById(String id) {
        return delegate.findById(id);
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null || product.getId().isBlank()){
            product.setId(UUID.randomUUID().toString());
        }
        return delegate.save(product);
    }

    @Override
    public void deleteById(String id) {
        delegate.deleteById(id);
    }
}
