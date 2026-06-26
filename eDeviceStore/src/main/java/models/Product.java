package models;

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
@Builder
@EqualsAndHashCode(of = "id")
@ToString
//@Entity
//@Table(name = "vehicle")

public class Product {
    private String id;
    private String description;
    private BigDecimal price;
    private String category;
    private int year;
    int quantity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Object> attributes = new HashMap<>();

    @Builder
    public Product(String id,
                   String description,
                   BigDecimal price,
                   String category,
                   int year,
                   int quantity,
                   Map<String, Object> attributes){
        this.id = id;
        this.description = description;
        this.price = price;
        this.category = category;
        this.year = year;
        this.quantity = quantity;
        this.attributes = attributes == null ? new HashMap<>() : new HashMap<>(attributes);
    }

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
