package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadBasketPort {
    Optional<Basket> loadBasket(UUID customerId);
    List<Basket> findAllByRestaurant(UUID restaurantId);
}