package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import java.util.UUID;

public record CheckoutRequest(UUID customerId, UUID restaurantId, String customerName, Address deliveryAddress
) {}
