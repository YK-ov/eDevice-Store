package com.example.edevicestore.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products")

public class Product {
    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String category;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(nullable = false)
    int quantity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Object> attributes = new HashMap<>();

    public Map<String, Object> getAttributes(){
        return Collections.unmodifiableMap(attributes);
    }

    public Object getAttribute(String key){
        return attributes.get(key);
    }

    public void addAttribute(String key, Object value){
        attributes.put(key, value);
    }

    public void removeAttribute(String key){
        attributes.remove(key);
    }

    public Product copy(){
        return Product.builder()
                .id(id)
                .description(description)
                .price(price)
                .category(category)
                .year(year)
                .quantity(quantity)
                .attributes(new HashMap<>(attributes))
                .build();
    }


}
