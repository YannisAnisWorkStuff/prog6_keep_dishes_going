package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.*;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.CreateGuestOrderCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.CreateOrderCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.CreateOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadBasketPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveOrderPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.adapter.out.SpringDomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final LoadBasketPort loadBasketPort;
    private final SaveOrderPort saveOrderPort;
    private final SpringDomainEventPublisher publishEventPort;

    public CreateOrderUseCaseImpl(
            LoadBasketPort loadBasketPort,
            SaveOrderPort saveOrderPort,
            SpringDomainEventPublisher publishEventPort
    ) {
        this.loadBasketPort = loadBasketPort;
        this.saveOrderPort = saveOrderPort;
        this.publishEventPort = publishEventPort;
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderCommand command) {
        Basket basket = loadBasketPort.loadBasket(command.customerId()).orElseThrow(() -> BasketId.basketNotFoundException(command.customerId()));

        List<OrderItem> orderItems = basket.getItems().stream()
                .map(basketItem -> new OrderItem(
                        basketItem.getDishId(),
                        basketItem.getName(),
                        basketItem.getPrice(),
                        basketItem.getQuantity()
                ))
                .collect(Collectors.toList());

        Order order = Order.createFromBasket(
                command.customerId(),
                basket.getRestaurantId(),
                orderItems,
                command.customerName(),
                command.address()
        );

        saveOrderPort.saveOrder(order);
        publishEventPort.publish(order.getDomainEvents());
        order.clearDomainEvents();

        return order;
    }

    @Transactional
    public Order createGuestOrder(CreateGuestOrderCommand command) {
        List<OrderItem> orderItems = command.items().stream()
                .map(item -> new OrderItem(
                        item.dishId(),
                        item.name(),
                        item.price(),
                        item.quantity()
                ))
                .collect(Collectors.toList());

        Order order = new Order(
                OrderId.create(),
                null,
                UUID.fromString(command.restaurantId()),
                orderItems,
                command.customerName(),
                command.deliveryAddress()
        );

        saveOrderPort.saveOrder(order);
        publishEventPort.publish(order.getDomainEvents());
        order.clearDomainEvents();

        return order;
    }

}