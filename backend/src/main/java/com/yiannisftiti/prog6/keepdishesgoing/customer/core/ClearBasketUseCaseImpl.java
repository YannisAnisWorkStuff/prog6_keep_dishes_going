package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.ClearBasketUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.UpdateBasketPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ClearBasketUseCaseImpl implements ClearBasketUseCase {

    private final UpdateBasketPort updateBasketPort;

    public ClearBasketUseCaseImpl(UpdateBasketPort updateBasketPort) {
        this.updateBasketPort = updateBasketPort;
    }

    @Override
    public void clearBasket(UUID customerId) {
        updateBasketPort.clearBasket(customerId);
    }
}