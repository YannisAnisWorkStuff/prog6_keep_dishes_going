package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.basket;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "basket")
public class BasketJPAEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private UUID restaurantId;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BasketItemJPAEntity> items = new ArrayList<>();

    public void addItem(BasketItemJPAEntity item) {
        item.setBasket(this);
        items.add(item);
    }

    public void clearItems() {
        items.forEach(i -> i.setBasket(null));
        items.clear();
    }

    public BasketJPAEntity(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<BasketItemJPAEntity> getItems() {
        return items;
    }

    public void setItems(List<BasketItemJPAEntity> items) {
        this.items = items;
    }
}