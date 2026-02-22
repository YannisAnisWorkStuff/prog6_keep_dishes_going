package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Dish;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.DishId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.DeleteDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveDishPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadDishPort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DishJPAAdapter implements SaveDishPort, LoadDishPort, DeleteDishPort {

    private final DishJPARepository repository;

    public DishJPAAdapter(DishJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveDish(RestaurantId restaurantId, Dish dish) {
        DishJPAEntity entity = new DishJPAEntity(
                dish.getId().id(),
                restaurantId.id(),
                dish.getName(),
                dish.getType().name(),
                dish.getDescription(),
                String.join(",", dish.getFoodTags()),
                dish.getPrice(),
                dish.getPictureUrl(),
                dish.getState().name()
        );
        repository.save(entity);
    }

    @Override
    public List<Dish> loadDishesByRestaurant(RestaurantId restaurantId) {
        return repository.findByRestaurantId(restaurantId.id())
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Dish loadDishById(DishId dishId) {
        DishJPAEntity dish = repository.findById(dishId.id()).orElse(null);
        return dish==null?null:toDomain(dish);
    }

    private Dish toDomain(DishJPAEntity entity) {
        List<String> tags = entity.getTags() != null && !entity.getTags().isBlank()
                ? Arrays.asList(entity.getTags().split(","))
                : new ArrayList<>();

        return new Dish(
                new DishId(entity.getId()),
                new RestaurantId(entity.getRestaurantId()),
                entity.getName(),
                Dish.Type.valueOf(entity.getType()),
                entity.getDescription(),
                entity.getPrice(),
                entity.getPictureUrl(),
                tags,
                Dish.State.valueOf(entity.getState())
        );
    }

    @Override
    public void deletedDish(DishId dishId) {
        repository.deleteById(dishId.id());
    }
}