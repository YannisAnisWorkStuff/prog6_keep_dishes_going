package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.order;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.OrderId;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.OrderItem;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.OrderStatus;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.UpdateOrderPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderJPAAdapter implements SaveOrderPort, UpdateOrderPort, LoadOrderPort {

    private final OrderJPARepository orderRepository;
    private final SnapshotRepository snapshotRepository;

    public OrderJPAAdapter(OrderJPARepository orderRepository, SnapshotRepository snapshotRepository) {
        this.orderRepository = orderRepository;
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        OrderJPAEntity entity = new OrderJPAEntity();
        entity.setId(order.getId().orderid());
        entity.setCustomerId(order.getCustomerId());
        entity.setRestaurantId(order.getRestaurantId());
        entity.setCustomerName(order.getCustomerName());
        entity.setDeliveryAddress(order.getDeliveryAddress());
        entity.setTotal(order.getTotal());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setStatus(order.getStatus().name());
        entity.setRejectionReason(null);

        entity.setItems(order.getItems().stream()
                .map(item -> mapOrderItemEntity(item, entity))
                .collect(Collectors.toList()));

        if (order.getDomainEvents().size() > 5) {
            OrderSnapshot snapshot = new OrderSnapshot(order);
            snapshotRepository.save(snapshot);
        }

        orderRepository.save(entity);
    }

    private OrderItemJPAEntity mapOrderItemEntity(OrderItem item, OrderJPAEntity orderEntity) {
        OrderItemJPAEntity itemEntity = new OrderItemJPAEntity();
        itemEntity.setDishId(item.dishId());
        itemEntity.setName(item.name());
        itemEntity.setPrice(item.price());
        itemEntity.setQuantity(item.quantity());
        itemEntity.setOrder(orderEntity);
        return itemEntity;
    }

    private Order mapToDomain(OrderJPAEntity entity) {
        Order order = new Order(
                new OrderId(entity.getId()),
                entity.getCustomerId(),
                entity.getRestaurantId(),
                entity.getItems().stream()
                        .map(i -> new OrderItem(i.getDishId(), i.getName(), i.getPrice(), i.getQuantity()))
                        .collect(Collectors.toList()),
                entity.getCustomerName(),
                entity.getDeliveryAddress()
        );
        order.setStatus(OrderStatus.valueOf(entity.getStatus()));
        order.setRejectionReason(entity.getRejectionReason());
        return order;
    }

    @Override
    @Transactional
    public void updateStatus(UUID orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }

    @Override
    public void updateRejectionReason(UUID orderId, String reason) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setRejectionReason(reason);
            orderRepository.save(order);
        });
    }

    @Override
    public Optional<Order> loadOrder(UUID orderId) {
        return orderRepository.findById(orderId).map(this::mapToDomain);
    }

}