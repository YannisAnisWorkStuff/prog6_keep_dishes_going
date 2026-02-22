package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import java.util.UUID;

public record UpdateBasketQuantityCommand(
        UUID customerId,
        UUID dishId,
        int quantity
) {}
