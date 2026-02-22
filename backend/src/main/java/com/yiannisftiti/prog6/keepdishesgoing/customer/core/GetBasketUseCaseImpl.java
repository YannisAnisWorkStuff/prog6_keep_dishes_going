package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.CustomerId;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.GetBasketUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadBasketPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetBasketUseCaseImpl implements GetBasketUseCase {
    private final LoadBasketPort loadBasketPort;

    public GetBasketUseCaseImpl(LoadBasketPort loadBasketPort) {
        this.loadBasketPort = loadBasketPort;
    }

    @Override
    public Optional<Basket> getBasket(CustomerId customerId) {
        return loadBasketPort.loadBasket(customerId.value());
    }
}