package models;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {
    private Map<Product, Integer> addedProducts;

    public void addToBasket(Product product) {
        int currentQuantity = 1;

        for (Map.Entry<Product, Integer> entry : addedProducts.entrySet()) {
            if (entry.getKey().equals(product)) {
                currentQuantity = entry.getValue() + 1;
            }
        }

        addedProducts.put(product, currentQuantity);
    }

    public void removeFromBasket(Product product) {
        int currentQuantity = 0;

        if (!addedProducts.containsKey(product)) {
             return;
        }

        if (addedProducts.get(product) > 1){
            addedProducts.put(product, addedProducts.get(product) - 1);
        }
        else {
            addedProducts.remove(product);
        }
    }

    public int getQuantity(Product product) {
        return addedProducts.get(product);
    }

    public boolean isInBasket(Product product) {
        return addedProducts.containsKey(product);
    }

    public Basket copy(){
        return Basket.builder()
                .addedProducts(addedProducts)
                .build();
    }

}
