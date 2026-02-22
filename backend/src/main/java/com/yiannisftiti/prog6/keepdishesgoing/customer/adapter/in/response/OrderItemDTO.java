package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.OrderItem;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDTO(
        UUID dishId,
        String name,
        BigDecimal price,
        int quantity
) {
    public static OrderItemDTO toItemDTO(OrderItem item) {
        return new OrderItemDTO(item.dishId(), item.name(), item.price(), item.quantity());
    }
}