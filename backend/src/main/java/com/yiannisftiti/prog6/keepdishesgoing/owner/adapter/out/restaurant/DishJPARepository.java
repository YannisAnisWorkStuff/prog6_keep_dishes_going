package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DishJPARepository extends JpaRepository<DishJPAEntity, UUID> {
    List<DishJPAEntity> findByRestaurantId(UUID restaurantId);
}