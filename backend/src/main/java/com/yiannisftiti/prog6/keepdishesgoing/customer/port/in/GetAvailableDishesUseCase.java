package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.AvailableDishProjection;
import java.util.List;
import java.util.UUID;

public interface GetAvailableDishesUseCase {
    List<AvailableDishProjection> getAvailableDishes(UUID restaurantId);
}
