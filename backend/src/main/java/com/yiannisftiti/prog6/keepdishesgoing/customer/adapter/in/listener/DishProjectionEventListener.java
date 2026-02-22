package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.listener;

import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.dish.AvailableDishJPAEntity;
import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.dish.AvailableDishJPARepository;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DishProjectionEventListener {

    private final AvailableDishJPARepository dishRepository;

    public DishProjectionEventListener(AvailableDishJPARepository repository) {
        this.dishRepository = repository;
    }

    @EventListener(DishPublishedEvent.class)
    public void handleDishPublished(DishPublishedEvent event) {
        AvailableDishJPAEntity entity = new AvailableDishJPAEntity();
        entity.setId(event.dishId());
        entity.setRestaurantId(event.restaurantId());
        entity.setName(event.dishName());
        entity.setState("PUBLISHED");
        entity.setPrice(event.price());
        dishRepository.save(entity);
    }

    @EventListener(DishUnpublishedEvent.class)
    public void handleDishUnpublished(DishUnpublishedEvent event) {
        dishRepository.findById(event.dishId())
                .ifPresent(d -> {
                    d.setState("UNPUBLISHED");
                    dishRepository.save(d);
                });
    }

    @EventListener(DishDeletedEvent.class)
    public void handleDishDeleted(DishDeletedEvent event) {
        dishRepository.deleteById(event.dish());
    }

    @EventListener(DishOutOfStockEvent.class)
    public void handleDishOutOfStock(DishOutOfStockEvent event) {
        dishRepository.findById(event.dishId())
                .ifPresent(d -> {
                    d.setState("OUT_OF_STOCK");
                    dishRepository.save(d);
                });
    }

    @EventListener(DishBackInStockEvent.class)
    public void handleDishBackInStock(DishBackInStockEvent event) {
        dishRepository.findById(event.dishId())
                .ifPresent(d -> {
                    d.setState("PUBLISHED");
                    d.setPrice(event.price());
                    dishRepository.save(d);
                });
    }
}
