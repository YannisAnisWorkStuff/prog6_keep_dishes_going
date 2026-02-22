package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.events;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;

import java.time.LocalDateTime;

public record DishEditedEvent(LocalDateTime eventPit, RestaurantId restaurantId, DishId dish)
        implements DomainEvent {

    public DishEditedEvent(RestaurantId restaurantId, DishId dishId) {
        this(LocalDateTime.now(), restaurantId, dishId);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}