package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.MarkDishOutOfStockUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MarkDishOutOfStockUseCaseImpl implements MarkDishOutOfStockUseCase {

    private final LoadRestarauntPort loadRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;
    private final SaveRestarauntPort saveRestaurantPort;
    private final PublishDomainEventsPort eventPublisher;

    public MarkDishOutOfStockUseCaseImpl(
            LoadRestarauntPort loadRestaurantPort,
            LoadDishPort loadDishPort,
            SaveDishPort saveDishPort,
            SaveRestarauntPort saveRestaurantPort,
            PublishDomainEventsPort eventPublisher) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void markOutOfStock(DishId dishId, RestaurantId restaurantId)
            throws RestaurantNotFoundException, DishNotFoundException {

        Restaurant restaurant = loadRestaurantPort.loadBy(restaurantId).orElseThrow(() -> RestaurantId.restaurantNotFoundException(restaurantId));

        Dish dish = loadDishPort.loadDishById(dishId);
        if (dish == null) throw DishId.dishNotFoundException(dishId);

        restaurant.markDishOutOfStock(dish);
        saveDishPort.saveDish(restaurantId, dish);
        saveRestaurantPort.saveRestarauntPort(restaurant);
        eventPublisher.publish(restaurant.getDomainEvents());
        restaurant.clearDomainEvents();
    }
}
