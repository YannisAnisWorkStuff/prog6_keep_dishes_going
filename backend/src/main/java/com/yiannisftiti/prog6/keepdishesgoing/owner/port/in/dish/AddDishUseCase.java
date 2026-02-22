package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.PublishedDishLimitExceededException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

public interface AddDishUseCase {
    Dish addDish(RestaurantId restaurantId, AddDishCommand command) throws RestaurantNotFoundException, PublishedDishLimitExceededException;
}
