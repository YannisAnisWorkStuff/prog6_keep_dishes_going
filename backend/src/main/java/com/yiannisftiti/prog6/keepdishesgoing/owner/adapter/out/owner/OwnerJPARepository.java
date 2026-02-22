package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.owner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnerJPARepository extends JpaRepository<OwnerJPAEntity, UUID> {

    Optional<OwnerJPAEntity> findByEmail(String email);

    Optional<OwnerJPAEntity> findByRestaurantId(UUID restaurantId);

}
