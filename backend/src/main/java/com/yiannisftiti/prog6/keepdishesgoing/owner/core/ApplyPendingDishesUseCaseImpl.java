package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.ApplyPendingDishesUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyPendingDishesUseCaseImpl implements ApplyPendingDishesUseCase {

    private final LoadRestarauntPort loadRestaurantPort;
    private final SaveRestarauntPort saveRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;
    private final PublishDomainEventsPort eventPublisher;

    public ApplyPendingDishesUseCaseImpl(
            LoadRestarauntPort loadRestaurantPort,
            SaveRestarauntPort saveRestaurantPort,
            LoadDishPort loadDishPort,
            SaveDishPort saveDishPort,
            PublishDomainEventsPort eventPublisher) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void applyAllPendingDishes(RestaurantId restaurantId)
            throws RestaurantNotFoundException {

        Restaurant restaurant = loadRestaurantPort.loadBy(restaurantId).orElseThrow(() -> RestaurantId.restaurantNotFoundException(restaurantId));

        List<Dish> dishes = loadDishPort.loadDishesByRestaurant(restaurantId);

        for (Dish dish : dishes) {
            if (dish.getState() == Dish.State.DRAFT) {
                restaurant.publishDish(dish);
                saveDishPort.saveDish(restaurantId, dish);
            }
        }

        saveRestaurantPort.saveRestarauntPort(restaurant);
        eventPublisher.publish(restaurant.getDomainEvents());
        restaurant.clearDomainEvents();
    }
}
