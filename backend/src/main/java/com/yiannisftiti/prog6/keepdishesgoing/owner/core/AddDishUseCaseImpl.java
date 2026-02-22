package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.PublishedDishLimitExceededException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.service.DishDomainService;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.AddDishCommand;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.AddDishUseCase;
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
public class AddDishUseCaseImpl implements AddDishUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AddDishUseCaseImpl.class);
    private final LoadRestarauntPort loadRestaurantPort;
    private final SaveDishPort saveDishPort;
    private final SaveRestarauntPort saveRestaurantPort;
    private final PublishDomainEventsPort eventPublisher;

    private DishDomainService dishDomainService;

    public AddDishUseCaseImpl(
            LoadRestarauntPort loadRestaurantPort,
            SaveRestarauntPort saveRestaurantPort,
            SaveDishPort saveDishPort,
            PublishDomainEventsPort eventPublisher,
            DishDomainService dishDomainService) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.saveDishPort = saveDishPort;
        this.eventPublisher = eventPublisher;
        this.dishDomainService = dishDomainService;
    }

    @Transactional
    @Override
    public Dish addDish(RestaurantId restaurantId, AddDishCommand command)
            throws RestaurantNotFoundException, PublishedDishLimitExceededException {

        logger.info("Adding dish '{}' to restaurant {}", command.name(), restaurantId.id());

        Restaurant restaurant = loadRestaurantPort.loadBy(restaurantId)
                .orElseThrow(() -> RestaurantId.restaurantNotFoundException(restaurantId));

        Dish newDish = command.toDomain();
        dishDomainService.checkOverLimit(restaurantId);
        restaurant.addDish(newDish);

        saveDishPort.saveDish(restaurantId, newDish);
        saveRestaurantPort.saveRestarauntPort(restaurant);

        eventPublisher.publish(restaurant.getDomainEvents());
        restaurant.clearDomainEvents();

        return newDish;
    }
}