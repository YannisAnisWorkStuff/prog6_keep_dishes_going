package com.yiannisftiti.prog6.keepdishesgoing.shared.event;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DishBackInStockEvent(LocalDateTime eventPit, UUID restaurantId, UUID dishId, String dishName, BigDecimal price) implements DomainEvent {

    public DishBackInStockEvent(UUID restaurantId, UUID dishId, String dishName, BigDecimal price) {
        this(LocalDateTime.now(), restaurantId, dishId, dishName, price);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}