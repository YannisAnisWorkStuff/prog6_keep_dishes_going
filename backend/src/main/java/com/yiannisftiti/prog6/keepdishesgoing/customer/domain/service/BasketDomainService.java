package com.yiannisftiti.prog6.keepdishesgoing.customer.domain.service;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.AvailableDishProjection;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.exception.DishUnavailableException;

import java.util.UUID;

public class BasketDomainService {

    public void validateDishAvailability(AvailableDishProjection dish, UUID restaurantId) {
        if (dish == null)
            throw new DishUnavailableException("Dish not found.");

        if (!dish.restaurantId().equals(restaurantId))
            throw new DishUnavailableException("Dish belongs to a different restaurant.");

        if (!"PUBLISHED".equalsIgnoreCase(dish.state()))
            throw new DishUnavailableException(dish.id());
    }
}
