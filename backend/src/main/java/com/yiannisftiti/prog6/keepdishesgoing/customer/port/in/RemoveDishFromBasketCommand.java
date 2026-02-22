package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import java.util.UUID;

public record RemoveDishFromBasketCommand(
        UUID customerId,
        UUID dishId
) {}