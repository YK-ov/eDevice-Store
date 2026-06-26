package models;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProductCategoryConfig {
    private final String category;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, String> attributes = new HashMap<>();

    @Builder
    public ProductCategoryConfig(String category, Map<String, String> attributes){
        this.category = category;
        this.attributes = attributes == null ? new HashMap<>() : new HashMap<>(attributes);
    }

    public Map<String, String> getAttributes(){
        return Collections.unmodifiableMap(attributes);
    }

    public void addAttributes(String name, String type){
        attributes.put(name, type);
    }

    public void removeAttributes(String name){
        attributes.remove(name);
    }

    public ProductCategoryConfig copy(){
        return ProductCategoryConfig.builder()
                .category(category)
                .attributes(new HashMap<>(attributes))
                .build();
    }

}
