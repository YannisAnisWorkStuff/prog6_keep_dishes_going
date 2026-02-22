package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        UUID restaurantId,
        String customerName,
        Address address
) {}