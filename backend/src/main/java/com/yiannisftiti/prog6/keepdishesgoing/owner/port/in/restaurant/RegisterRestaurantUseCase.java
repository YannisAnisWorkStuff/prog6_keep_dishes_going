package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;

public interface RegisterRestaurantUseCase {

    Restaurant registerRestaurant(RegisterRestaurantCommand command);

}
