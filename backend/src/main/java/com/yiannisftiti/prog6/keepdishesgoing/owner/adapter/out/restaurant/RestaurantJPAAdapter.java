package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.restaurant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.*;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadRestarauntPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.SaveRestarauntPort;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RestaurantJPAAdapter implements LoadRestarauntPort, SaveRestarauntPort {

    private final RestaurantJPARepository repository;
    private final ObjectMapper mapper;

    public RestaurantJPAAdapter(RestaurantJPARepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.mapper = objectMapper;
    }

    private Restaurant toDomain(RestaurantJPAEntity entity) {
        List<DaySchedule> schedules;
        try {
            schedules = entity.getSchedulesJson() != null ? mapper.readValue(entity.getSchedulesJson(),
                    mapper.getTypeFactory().constructCollectionType(List.class, DaySchedule.class))
                    : Collections.emptyList();
        } catch (JsonProcessingException exc) {
            throw new IllegalStateException("Failed to ddeserialize schedules JSON", exc);
        }

        return new Restaurant(
                new RestaurantId(entity.getUuid()),
                new OwnerId(entity.getOwnerId()),
                entity.getName(),
                entity.getAddress(),
                entity.getCuisineType(),
                entity.getEmail(),
                entity.getPictures(),
                entity.getPreparationTimeMinutes(),
                schedules
        );
    }

    @Override
    public Optional<Restaurant> loadBy(RestaurantId restaurantId) {
        return repository.findById(restaurantId.id())
                .map(this::toDomain);
    }

    @Override
    public Optional<Restaurant> loadByOwner(OwnerId ownerId) {
        return repository.findByOwnerId(ownerId.id()).map(this::toDomain);
    }

    @Override
    public List<Restaurant> loadAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private RestaurantJPAEntity toEntity(Restaurant restaurant) {
        return new RestaurantJPAEntity(
                restaurant.getId().id(),
                restaurant.getOwnerId().id(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getEmail(),
                restaurant.getPictures(),
                restaurant.getPreparationTimeMinutes(),
                serializeSchedules(restaurant.getSchedules())
        );
    }

    private String serializeSchedules(List<DaySchedule> schedules) {
        try {
            return (schedules == null || schedules.isEmpty()) ? "[]" : mapper.writeValueAsString(schedules);
        } catch (JsonProcessingException exc) {
            throw new IllegalStateException("Failed to serialize schedules to JSON", exc);
        }
    }


    @Override
    public void saveRestarauntPort(Restaurant restaurant) {
        repository.save(toEntity(restaurant));
    }
}
