package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import java.math.BigDecimal;
import java.util.*;

public class Basket {

    private final BasketId id;
    private final UUID customerId;
    private UUID restaurantId;
    private final List<BasketItem> items = new ArrayList<>();

    public Basket(BasketId id, UUID customerId, UUID restaurantId) {
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
    }

    public BasketId getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public UUID getRestaurantId() { return restaurantId; }
    public List<BasketItem> getItems() { return items;}

    public void addDish(UUID dishId, String name, BigDecimal price, int quantity) {
        items.stream()
                .filter(i -> i.getDishId().equals(dishId))
                .findFirst()
                .ifPresentOrElse(
                        existing -> existing.increaseQuantity(quantity),
                        () -> items.add(new BasketItem(dishId, name, price, quantity))
                );
    }

    public void removeDish(UUID dishId) {
        items.removeIf(i -> i.getDishId().equals(dishId));
    }

    public void clear() {
        items.clear();
    }


    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId=restaurantId;
    }

    public BigDecimal totalPrice() {
        return items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateDishDetails(UUID dishId, BigDecimal price) {
        items.stream()
                .filter(i -> i.getDishId().equals(dishId))
                .findFirst()
                .ifPresent(i -> {
                    i.setPrice(price);});
    }


}
