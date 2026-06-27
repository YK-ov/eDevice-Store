package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, String> {

}
