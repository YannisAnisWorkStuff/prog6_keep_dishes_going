package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderAcceptedEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderCreatedEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.order.OrderRejectedEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    private final OrderId id;
    private final UUID customerId;
    private final UUID restaurantId;
    private final String customerName;
    private final Address deliveryAddress;
    private final List<OrderItem> items;
    private final BigDecimal total;
    private final LocalDateTime createdAt;
    private OrderStatus status;

    private String rejectionReason;

    private List<DomainEvent> domainEvents = new ArrayList<>();

    public Order(OrderId id, UUID customerId, UUID restaurantId, List<OrderItem> items, String customerName, Address deliveryAddress) {
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.items = items;
        this.total = items.stream().map(OrderItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public static Order createFromBasket(UUID customerId, UUID restaurantId, List<OrderItem> items, String customerName, Address deliveryAddress) {
        Order order = new Order(OrderId.create(), customerId, restaurantId, items, customerName, deliveryAddress);
        order.domainEvents.add(new OrderCreatedEvent(order.id.orderid(), restaurantId, customerId, order.total));
        return order;
    }

    public OrderId getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public UUID getRestaurantId() { return restaurantId; }
    public String getCustomerName() { return customerName; }
    public Address getDeliveryAddress() { return deliveryAddress; }
    public List<OrderItem> getItems() { return items; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public OrderStatus getStatus() { return status; }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void setStatus(OrderStatus status) {
        this.status=status;
    }

}
