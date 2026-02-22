package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.dish;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.AvailableDishProjection;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadAvailableDishesPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AvailableDishProjectionAdapter implements LoadAvailableDishesPort {

    private final AvailableDishJPARepository repository;

    public AvailableDishProjectionAdapter(AvailableDishJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AvailableDishProjection> loadAvailableDishes(UUID restaurantId) {
        return repository.findByRestaurantIdAndState(restaurantId, "PUBLISHED")
                .stream()
                .map(d -> new AvailableDishProjection(
                        d.getId(),
                        d.getRestaurantId(),
                        d.getName(),
                        d.getPrice(),
                        d.getState()
                ))
                .toList();
    }
}