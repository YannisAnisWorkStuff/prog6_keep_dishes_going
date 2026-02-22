package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.events;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishAddedEvent(LocalDateTime eventPit, RestaurantId restaurantId, DishId dishId, String dishName) implements DomainEvent {

    public DishAddedEvent(RestaurantId restaurantId, DishId dishId, String dishName) {
        this(LocalDateTime.now(), restaurantId, dishId, dishName);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
