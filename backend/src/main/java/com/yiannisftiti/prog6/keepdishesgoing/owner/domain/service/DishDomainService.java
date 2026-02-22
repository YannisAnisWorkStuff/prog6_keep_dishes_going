package com.yiannisftiti.prog6.keepdishesgoing.owner.domain.service;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.PublishedDishLimitExceededException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadDishPort;
import org.springframework.stereotype.Service;

@Service
public class DishDomainService {

    private final LoadDishPort loadDishPort;

    public DishDomainService(LoadDishPort loadDishPort) {
        this.loadDishPort = loadDishPort;
    }

    public int getPublishedDishCount(RestaurantId restaurantId) {
        return (int) loadDishPort.loadDishesByRestaurant(restaurantId)
                .stream()
                .filter(Dish::isPublished)
                .count();
    }

    public void checkOverLimit(RestaurantId restaurantId) {
        if(getPublishedDishCount(restaurantId) >= Restaurant.MAX_PUBLISHED_DISHES) throw new PublishedDishLimitExceededException(restaurantId);
    }

}