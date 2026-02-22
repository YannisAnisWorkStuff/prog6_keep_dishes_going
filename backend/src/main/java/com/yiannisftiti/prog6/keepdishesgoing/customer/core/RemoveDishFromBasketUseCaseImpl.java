package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.RemoveDishFromBasketCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.RemoveDishFromBasketUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadBasketPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveBasketPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RemoveDishFromBasketUseCaseImpl implements RemoveDishFromBasketUseCase {

    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;

    public RemoveDishFromBasketUseCaseImpl(LoadBasketPort loadBasketPort, SaveBasketPort saveBasketPort) {
        this.loadBasketPort = loadBasketPort;
        this.saveBasketPort = saveBasketPort;
    }

    @Override
    public void removeDishFromBasket(RemoveDishFromBasketCommand command) {
        Basket basket = loadBasketPort.loadBasket(command.customerId()).orElseThrow(() -> new IllegalStateException("Basket not found for customer: " + command.customerId()));

        basket.removeDish(command.dishId());

        saveBasketPort.saveBasket(basket);
    }
}
