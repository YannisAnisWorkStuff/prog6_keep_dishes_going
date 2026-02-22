package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class BasketItem {
    private final UUID dishId;
    private final String name;
    private BigDecimal price;
    private int quantity;

    public BasketItem(UUID dishId, String name, BigDecimal price, int quantity) {
        this.dishId = dishId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getDishId() { return dishId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void increaseQuantity(int q) { this.quantity += q; }
    public void setPrice(BigDecimal price) {
        this.price=price;
    }
}