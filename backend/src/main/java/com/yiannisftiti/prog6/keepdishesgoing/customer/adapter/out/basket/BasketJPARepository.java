package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.basket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface BasketJPARepository extends JpaRepository<BasketJPAEntity, UUID> {
    Optional<BasketJPAEntity> findByCustomerId(UUID customerId);
    List<BasketJPAEntity> findByRestaurantId(UUID restaurantId);
}