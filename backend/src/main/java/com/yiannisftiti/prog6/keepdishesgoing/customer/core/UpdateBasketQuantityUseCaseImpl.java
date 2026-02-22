package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.UpdateBasketQuantityCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.UpdateBasketQuantityUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadBasketPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveBasketPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateBasketQuantityUseCaseImpl implements UpdateBasketQuantityUseCase {

    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;

    public UpdateBasketQuantityUseCaseImpl(LoadBasketPort loadBasketPort, SaveBasketPort saveBasketPort) {
        this.loadBasketPort = loadBasketPort;
        this.saveBasketPort = saveBasketPort;
    }

    @Override
    public void updateBasketQuantity(UpdateBasketQuantityCommand command) {
        Basket basket = loadBasketPort.loadBasket(command.customerId())
                .orElseThrow(() -> new IllegalStateException("Basket not found for customer: " + command.customerId()));

        basket.getItems().stream()
                .filter(i -> i.getDishId().equals(command.dishId()))
                .findFirst()
                .ifPresent(item -> item.increaseQuantity(command.quantity() - item.getQuantity()));

        saveBasketPort.saveBasket(basket);
    }
}