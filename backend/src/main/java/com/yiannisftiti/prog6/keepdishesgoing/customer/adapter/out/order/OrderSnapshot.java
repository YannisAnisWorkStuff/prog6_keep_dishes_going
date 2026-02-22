package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_snapshots")
public class OrderSnapshot {

    @Id
    private UUID orderId;
    @Lob
    private String serializedState;
    private int version;

    protected OrderSnapshot() {}

    public OrderSnapshot(Order order) {
        this.orderId = order.getId().orderid();
        this.serializedState = serialize(order);
        this.version = 1;
    }

    private String serialize(Order order) {
        try {
            return new ObjectMapper().writeValueAsString(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
