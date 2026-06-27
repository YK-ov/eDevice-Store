package com.example.edevicestore.models;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {
    private User user;
    private List<BasketItem> addedProducts;

    public void addToBasket(Product product) {
        for (BasketItem item : addedProducts) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        addedProducts.add(BasketItem.builder().user(this.user).product(product).quantity(1).build());
    }

    public void removeFromBasket(Product product) {
        BasketItem toRemove = null;
        for (BasketItem item : addedProducts) {
            if (item.getProduct().equals(product)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    return;
                } else {
                    toRemove = item;
                }
            }
        }
        if (toRemove != null) {
            addedProducts.remove(toRemove);
        }
    }

    public Basket copy(){
        return Basket.builder()
                .addedProducts(addedProducts)
                .build();
    }

}
