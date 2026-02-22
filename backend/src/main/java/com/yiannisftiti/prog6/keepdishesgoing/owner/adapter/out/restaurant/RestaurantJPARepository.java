package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantJPARepository extends JpaRepository<RestaurantJPAEntity, UUID> {

    Optional<RestaurantJPAEntity> findByOwnerId(UUID ownerId);

}
