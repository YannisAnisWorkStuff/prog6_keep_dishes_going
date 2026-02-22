package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderDTO(
        UUID id,
        UUID customerId,
        UUID restaurantId,
        String customerName,
        Address deliveryAddress,
        List<OrderItemDTO> items,
        BigDecimal total,
        LocalDateTime createdAt,
        String status,
        String rejectionReason
) {

    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId().orderid(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getCustomerName(),
                order.getDeliveryAddress(),
                order.getItems().stream().map(OrderItemDTO::toItemDTO).collect(Collectors.toList()),
                order.getTotal(),
                order.getCreatedAt(),
                order.getStatus().name(),
                order.getRejectionReason()
        );
    }


}
