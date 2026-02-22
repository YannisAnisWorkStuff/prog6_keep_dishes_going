package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

public interface SaveDishPort {
    void saveDish(RestaurantId restaurantId, Dish dish);
}