package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.DishNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.EditDishAsDraftCommand;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.EditDishAsDraftUseCase;
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
public class EditDishAsDraftUseCaseImpl implements EditDishAsDraftUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EditDishAsDraftUseCaseImpl.class);

    private final LoadRestarauntPort loadRestaurantPort;
    private final SaveRestarauntPort saveRestaurantPort;
    private final PublishDomainEventsPort eventPublisher;
    private final SaveDishPort saveDishPort;
    private final LoadDishPort loadDishPort;

    public EditDishAsDraftUseCaseImpl(
            LoadRestarauntPort loadRestaurantPort,
            SaveRestarauntPort saveRestaurantPort, SaveDishPort saveDishPort,
            LoadDishPort loadDishPort,
            PublishDomainEventsPort eventPublisher) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.saveDishPort=saveDishPort;
        this.eventPublisher = eventPublisher;
        this.loadDishPort=loadDishPort;
    }

    @Transactional
    @Override
    public void editDishAsDraft(EditDishAsDraftCommand command)
            throws RestaurantNotFoundException, DishNotFoundException {

        Restaurant restaurant = loadRestaurantPort.loadBy(command.restaurantId()).orElseThrow(() -> RestaurantId.restaurantNotFoundException(command.restaurantId()));

        logger.info("Editing dish '{}' to restaurant {}", command.name(), command.restaurantId().id());
        Dish existingDish = loadDishPort.loadDishById(command.dishId());
        if (existingDish == null) throw DishId.dishNotFoundException(command.dishId());

        Dish updatedDish = new Dish(
                command.dishId(),
                command.restaurantId(),
                command.name(),
                command.type(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                command.foodTags(),
                Dish.State.DRAFT
        );

        restaurant.editDish(existingDish, updatedDish);

        saveDishPort.saveDish(command.restaurantId(), updatedDish);
        saveRestaurantPort.saveRestarauntPort(restaurant);

        eventPublisher.publish(restaurant.getDomainEvents());
        restaurant.clearDomainEvents();
        logger.info("Editing dish '{}' successfully!", command.name());
    }

}