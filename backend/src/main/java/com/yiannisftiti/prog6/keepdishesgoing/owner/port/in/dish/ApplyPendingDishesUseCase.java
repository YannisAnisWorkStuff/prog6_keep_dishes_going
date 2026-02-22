package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

public interface ApplyPendingDishesUseCase {
    void applyAllPendingDishes(RestaurantId restaurantId) throws RestaurantNotFoundException;
}