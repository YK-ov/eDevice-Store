package com.example.edevicestore.web;

import com.example.edevicestore.models.ProductCategoryConfig;
import com.example.edevicestore.repositories.impl.ProductCategoryConfigJsonRepository;
import com.example.edevicestore.services.impl.ProductCategoryConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")

public class CategoriesController {
    private final ProductCategoryConfigService productCategoryConfigService;

    public CategoriesController(ProductCategoryConfigService productCategoryConfigService) {
        this.productCategoryConfigService = productCategoryConfigService;
    }

    @GetMapping("/{category}")
    public ProductCategoryConfig get(@PathVariable String category){
        return productCategoryConfigService.getByCategory(category);
    }

    @GetMapping
    public List<ProductCategoryConfig> list(){
        return productCategoryConfigService.findAllCategories();
    }

}
