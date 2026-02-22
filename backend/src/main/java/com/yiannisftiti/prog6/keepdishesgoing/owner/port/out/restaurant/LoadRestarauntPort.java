package com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;

import java.util.List;
import java.util.Optional;

public interface LoadRestarauntPort {

    Optional<Restaurant> loadBy(RestaurantId restaurantId);
    Optional<Restaurant> loadByOwner(OwnerId ownerId);

    List<Restaurant> loadAll();

}
