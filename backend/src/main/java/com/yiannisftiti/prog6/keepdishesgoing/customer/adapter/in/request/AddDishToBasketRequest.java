package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request;

import java.util.UUID;

public record AddDishToBasketRequest(
        UUID restaurantId,
        UUID dishId,
        int quantity
) {}