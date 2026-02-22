package com.yiannisftiti.prog6.keepdishesgoing.owner.core;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Restaurant;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant.EditRestaurantCommand;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant.EditRestaurantUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.PublishDomainEventsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditRestaurantUseCaseImpl implements EditRestaurantUseCase {

    private final LoadRestarauntPort loadRestaurantPort;
    private final SaveRestarauntPort saveRestaurantPort;
    private final PublishDomainEventsPort eventPublisher;

    public EditRestaurantUseCaseImpl(
            LoadRestarauntPort loadRestaurantPort,
            SaveRestarauntPort saveRestaurantPort,
            PublishDomainEventsPort eventPublisher
    ) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public Restaurant editRestaurant(EditRestaurantCommand command) throws RestaurantNotFoundException {
        Restaurant restaurant = loadRestaurantPort.loadBy(command.restaurantId())
                .orElseThrow(() -> RestaurantId.restaurantNotFoundException(command.restaurantId()));

        restaurant.setName(command.name());
        restaurant.setAddress(command.address());
        restaurant.setCuisineType(command.cuisineType());
        restaurant.setEmail(command.email());
        restaurant.setPictures(command.pictures());
        restaurant.setPreparationTimeMinutes(command.preparationTimeMinutes());
        restaurant.setSchedules(command.schedules());

        saveRestaurantPort.saveRestarauntPort(restaurant);

        eventPublisher.publish(restaurant.getDomainEvents());
        restaurant.clearDomainEvents();
        return restaurant;
    }
}
