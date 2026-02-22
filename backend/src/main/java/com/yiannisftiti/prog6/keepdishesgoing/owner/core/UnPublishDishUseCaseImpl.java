package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.UnpublishDishUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnPublishDishUseCaseImpl implements UnpublishDishUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UnPublishDishUseCaseImpl.class);

    private final LoadRestarauntPort loadRestaurantPort;
    private final SaveRestarauntPort saveRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;
    private final PublishDomainEventsPort eventPublisher;

    public UnPublishDishUseCaseImpl(
            LoadRestarauntPort loadRestaurantPort,
            SaveRestarauntPort saveRestaurantPort,
            LoadDishPort loadDishPort,
            SaveDishPort saveDishPort,
            PublishDomainEventsPort eventPublisher
    ) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.saveDishPort = saveDishPort;
        this.eventPublisher = eventPublisher;
        this.loadDishPort = loadDishPort;
    }

    @Transactional
    @Override
    public void unpublishDish(DishId dishId, RestaurantId restaurantId)
            throws RestaurantNotFoundException, DishNotFoundException {

        Restaurant restaurant = loadRestaurantPort.loadBy(restaurantId)
                .orElseThrow(() -> RestaurantId.restaurantNotFoundException(restaurantId));

        Dish dish = loadDishPort.loadDishById(dishId);
        if (dish == null) throw DishId.dishNotFoundException(dishId);

        restaurant.unpublishDish(dish);
        saveDishPort.saveDish(restaurantId, dish);
        saveRestaurantPort.saveRestarauntPort(restaurant);

        eventPublisher.publish(restaurant.getDomainEvents());
        restaurant.clearDomainEvents();
        logger.info("Dish {} unpublished!", dish.getName());
    }

}