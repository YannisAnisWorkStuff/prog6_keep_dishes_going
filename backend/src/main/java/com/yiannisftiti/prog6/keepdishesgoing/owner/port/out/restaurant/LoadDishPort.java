package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

import java.util.List;

public interface LoadDishPort {
    List<Dish> loadDishesByRestaurant(RestaurantId restaurantId);
    Dish loadDishById(DishId dishId);
}
