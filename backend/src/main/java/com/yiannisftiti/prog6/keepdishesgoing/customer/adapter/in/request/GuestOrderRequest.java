package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request;

import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response.OrderItemDTO;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

import java.util.List;

public record GuestOrderRequest(
        String customerName,
        String restaurantId,
        Address deliveryAddress,
        List<OrderItemDTO> items
) {}
