package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.dish;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "available_dishes")
public class AvailableDishJPAEntity {

    @Id
    private UUID id;
    private UUID restaurantId;
    private String name;
    private BigDecimal price;
    private String state;

    public AvailableDishJPAEntity() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return  price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
