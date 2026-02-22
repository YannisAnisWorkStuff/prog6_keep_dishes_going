package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.listener;

import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadBasketPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveBasketPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasketEventListener {

    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;

    public BasketEventListener(LoadBasketPort loadBasketPort, SaveBasketPort saveBasketPort) {
        this.loadBasketPort = loadBasketPort;
        this.saveBasketPort = saveBasketPort;
    }

    @EventListener(DishUnpublishedEvent.class)
    @Transactional
    public void handleDishUnpublished(DishUnpublishedEvent event) {
        loadBasketPort.findAllByRestaurant(event.restaurantId())
                .forEach(basket -> {
                    basket.removeDish(event.dishId());
                    saveBasketPort.saveBasket(basket);
                });
    }

    @EventListener(DishOutOfStockEvent.class)
    @Transactional
    public void handleDishOutOfStock(DishOutOfStockEvent event) {
        loadBasketPort.findAllByRestaurant(event.restaurantId())
                .forEach(basket -> {
                    basket.removeDish(event.dishId());
                    saveBasketPort.saveBasket(basket);
                });
    }

    @EventListener(DishDeletedEvent.class)
    @Transactional
    public void handleDishDeleted(DishDeletedEvent event) {
        loadBasketPort.findAllByRestaurant(event.restaurantId())
                .forEach(basket -> {
                    basket.removeDish(event.dish());
                    saveBasketPort.saveBasket(basket);
                });
    }

    @EventListener(DishPublishedEvent.class)
    @Transactional
    public void handleDishPublished(DishPublishedEvent event) {
        loadBasketPort.findAllByRestaurant(event.restaurantId())
                .forEach(basket -> {
                    basket.updateDishDetails(
                            event.dishId(),
                            event.price()
                    );
                    saveBasketPort.saveBasket(basket);
                });
    }



}
