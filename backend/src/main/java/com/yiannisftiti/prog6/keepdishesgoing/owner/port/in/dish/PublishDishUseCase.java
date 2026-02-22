package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

import java.util.UUID;

public interface PublishDishUseCase {
    void publishDish(DishId dishId, RestaurantId restaurantId) throws RestaurantNotFoundException, DishNotFoundException;
}
