package com.yiannisftiti.prog6.keepdishesgoing.customer.port.out;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.AvailableDishProjection;
import java.util.List;
import java.util.UUID;

public interface LoadAvailableDishesPort {
    List<AvailableDishProjection> loadAvailableDishes(UUID restaurantId);
}