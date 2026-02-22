package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

public interface EditRestaurantUseCase {
    Restaurant editRestaurant(EditRestaurantCommand command) throws RestaurantNotFoundException;
}
