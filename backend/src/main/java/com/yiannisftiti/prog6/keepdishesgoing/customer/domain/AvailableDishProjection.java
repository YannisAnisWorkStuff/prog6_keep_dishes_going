package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record AvailableDishProjection(
        UUID id,
        UUID restaurantId,
        String name,
        BigDecimal price,
        String state
) {}
