package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.DeleteDishUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.*;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteDishUseCaseImpl implements DeleteDishUseCase {

    private static final Logger logger = LoggerFactory.getLogger(DeleteDishUseCaseImpl.class);

    private final LoadRestarauntPort loadRestaurantPort;
    private final SaveRestarauntPort saveRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final DeleteDishPort deleteDishPort;
    private final PublishDomainEventsPort eventPublisher;

    public DeleteDishUseCaseImpl(
            LoadRestarauntPort loadRestaurantPort,
            SaveRestarauntPort saveRestaurantPort,
            LoadDishPort loadDishPort,
            DeleteDishPort deleteDishPort,
            PublishDomainEventsPort eventPublisher
    ) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.loadDishPort = loadDishPort;
        this.deleteDishPort = deleteDishPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void deleteDish(DishId dishId, RestaurantId restaurantId)
            throws RestaurantNotFoundException, DishNotFoundException {

        logger.info("Deleting dish {} from restaurant {}", dishId.id(), restaurantId.id());

        Restaurant restaurant = loadRestaurantPort.loadBy(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + restaurantId.id()));

        Dish dish = loadDishPort.loadDishById(dishId);
        if (dish == null) {
            throw new DishNotFoundException("Dish not found: " + dishId.id());
        }
        restaurant.deleteDish(dish);
        deleteDishPort.deletedDish(dishId);
        saveRestaurantPort.saveRestarauntPort(restaurant);
        eventPublisher.publish(restaurant.getDomainEvents());
        restaurant.clearDomainEvents();

        logger.info("Dish {} deleted from restaurant {}", dishId.id(), restaurantId.id());
    }
}
