package com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;

import java.util.List;

public interface GetRestaurantUseCase {

    Restaurant getRestaurantByOwnerId(OwnerId ownerId) throws RestaurantNotFoundException;
    List<Restaurant> getRestaurants();
    Restaurant getRestaurantById(RestaurantId id) throws RestaurantNotFoundException;
    Double getGuesstimate(RestaurantId id);
}
