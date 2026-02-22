package com.yiannisftiti.prog6.keepdishesgoing.customer.core;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.AvailableDishProjection;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.BasketId;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.service.BasketDomainService;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.AddDishToBasketCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.AddDishToBasketUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AddDishToBasketUseCaseImpl implements AddDishToBasketUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AddDishToBasketUseCaseImpl.class);

    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;
    private final LoadAvailableDishesPort loadAvailableDishesPort;
    private final BasketDomainService basketDomainService = new BasketDomainService();

    public AddDishToBasketUseCaseImpl(LoadBasketPort loadBasketPort, SaveBasketPort saveBasketPort, LoadAvailableDishesPort loadAvailableDishesPort) {
        this.loadBasketPort = loadBasketPort;
        this.saveBasketPort = saveBasketPort;
        this.loadAvailableDishesPort = loadAvailableDishesPort;
    }

    @Override
    @Transactional
    public void addDishToBasket(AddDishToBasketCommand command) {
        Basket basket = loadBasketPort.loadBasket(command.customerId())
                .orElseGet(() -> new Basket(
                        BasketId.create(),
                        command.customerId(),
                        command.restaurantId()
                ));

        if(Objects.equals(basket.getRestaurantId(), new UUID(0, 0))) {
            basket.setRestaurantId(command.restaurantId());
        }
        if (!basket.getRestaurantId().equals(command.restaurantId())) {
            throw new RuntimeException("Basket belongs to restaurant" + basket.getRestaurantId() + ", but tried to add dish from restaurant: " + command.restaurantId());
        }


        AvailableDishProjection dish = loadAvailableDishesPort.loadAvailableDishes(command.restaurantId())
                .stream()
                .filter(d -> d.id().equals(command.dishId()))
                .findFirst()
                .orElse(null);
        basketDomainService.validateDishAvailability(dish, command.restaurantId());

        basket.addDish(dish.id(), dish.name(), dish.price(), command.quantity());

        saveBasketPort.saveBasket(basket);

        logger.info("Item {} has been added to basket!", dish.name());
    }
}
