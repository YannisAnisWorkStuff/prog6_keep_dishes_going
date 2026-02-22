package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import java.util.UUID;

public record AddDishToBasketCommand(
        UUID customerId,
        UUID restaurantId,
        UUID dishId,
        int quantity
) {}