package com.yiannisftiti.prog6.keepdishesgoing.customer.port.in;

import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response.OrderItemDTO;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

import java.util.List;

public record CreateGuestOrderCommand(
        String customerName,
        String restaurantId,
        Address deliveryAddress,
        List<OrderItemDTO> items
) {}