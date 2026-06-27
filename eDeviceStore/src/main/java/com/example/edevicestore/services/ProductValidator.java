package com.example.edevicestore.services;

import com.example.edevicestore.models.Product;
import com.example.edevicestore.models.ProductCategoryConfig;
import com.example.edevicestore.services.impl.ProductCategoryConfigService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductValidator {
    private final ProductCategoryConfigService productCategoryConfig;

    public ProductValidator(ProductCategoryConfigService productCategoryConfig) {
        this.productCategoryConfig = productCategoryConfig;
    }

    public void validate(Product product) {
        if (product == null){
            throw new IllegalStateException("Produkt nie moze byc nullem");
        }

        validateBaseFields(product);
        validateAttributes(product.getAttributes(), productCategoryConfig.getByCategory(product.getCategory()));
    }

    private void validateBaseFields(Product product) {
        requireNonBlank(product.getDescription(), "Opis produktu jest wymagany");
        requireNonBlank(product.getCategory(), "Kategoria produktu jest wymagana");

        if (product.getYear() <= 0){
            throw new IllegalArgumentException("Rok musi byc dodatni");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Cena nie moze byc ujemna");
        }

        if (product.getQuantity() < 0){
            throw new IllegalArgumentException("Ilosc produktu nie moze byc ujemna");
        }

    }

    private void validateAttributes(Map<String, Object> actualAttributes, ProductCategoryConfig config) {
        Map<String, String> expectedAttributes = config.getAttributes();

        for (String actualName : actualAttributes.keySet()) {
            if (!expectedAttributes.containsKey(actualName)){
                throw new IllegalArgumentException("Nieobslugiwany atrybut dla kategorii " + config.getCategory() + ": " + actualName);
            }
        }

        expectedAttributes.forEach((attrName, expectedType) -> {
            Object value = actualAttributes.get(attrName);

            if (value == null){
                throw new IllegalArgumentException("Brak wymaganego atrybutu: " + attrName);
            }

            if (expectedType.equalsIgnoreCase("string") && value instanceof String str){
                requireNonBlank(str, "Atrybut " + attrName + " nie moze byc pusty");
            }

            boolean isValidType = switch (expectedType.toLowerCase()){
                case "string" -> value instanceof String;
                case "number" -> value instanceof Number;
                case "boolean" -> value instanceof Boolean;
                case "integer" -> value instanceof Integer;
                default -> throw new IllegalStateException("Nieobslugiwany typ w configu: " + expectedType);
            };

            if (!isValidType){
                throw new IllegalArgumentException("Atrybut : " + attrName + " musi byc typu " + expectedType + ".");
            }
        });
    }


    private void requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }


}
