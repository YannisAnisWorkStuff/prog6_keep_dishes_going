package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

public interface MarkDishBackInStockUseCase {
    void markBackInStock(DishId dishId, RestaurantId restaurantId) throws RestaurantNotFoundException, DishNotFoundException;
}
