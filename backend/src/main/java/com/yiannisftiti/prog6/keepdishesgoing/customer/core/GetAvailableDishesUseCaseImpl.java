package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.AvailableDishProjection;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.GetAvailableDishesUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadAvailableDishesPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetAvailableDishesUseCaseImpl implements GetAvailableDishesUseCase {

    private final LoadAvailableDishesPort loadAvailableDishesPort;

    public GetAvailableDishesUseCaseImpl(LoadAvailableDishesPort loadAvailableDishesPort) {
        this.loadAvailableDishesPort = loadAvailableDishesPort;
    }

    @Override
    public List<AvailableDishProjection> getAvailableDishes(UUID restaurantId) {
        return loadAvailableDishesPort.loadAvailableDishes(restaurantId);
    }
}
