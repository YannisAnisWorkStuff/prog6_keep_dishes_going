package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.GetOrderStatusUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadOrderPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetOrderStatusUseCaseImpl implements GetOrderStatusUseCase {

    private final LoadOrderPort loadOrderPort;

    public GetOrderStatusUseCaseImpl(LoadOrderPort loadOrderPort) {
        this.loadOrderPort = loadOrderPort;
    }

    @Override
    public Optional<Order> getOrder(UUID orderId) {
        return loadOrderPort.loadOrder(orderId);
    }
}