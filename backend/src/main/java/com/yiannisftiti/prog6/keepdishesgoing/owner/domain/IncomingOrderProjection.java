package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class IncomingOrderProjection {

    private final UUID orderId;
    private final UUID restaurantId;
    private final UUID customerId;
    private final BigDecimal total;
    private final LocalDateTime createdAt;
    private OrderDecisionStatus status;

    public IncomingOrderProjection(UUID orderId, UUID restaurantId, UUID customerId, BigDecimal total) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.total = total;
        this.createdAt = LocalDateTime.now();
        this.status = OrderDecisionStatus.PENDING;
    }

    public IncomingOrderProjection(UUID orderId, UUID restaurantId, UUID customerId, BigDecimal total, LocalDateTime createdAt, OrderDecisionStatus status) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.total = total;
        this.createdAt = createdAt;
        this.status = status;
    }

    public UUID getOrderId() { return orderId; }
    public UUID getRestaurantId() { return restaurantId; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public OrderDecisionStatus getStatus() { return status; }

    public void accept() { this.status = OrderDecisionStatus.ACCEPTED; }
    public void reject() { this.status = OrderDecisionStatus.REJECTED; }
    public void pending() { this.status = OrderDecisionStatus.PENDING; }
    public void ready() { this.status = OrderDecisionStatus.READY; }
}
