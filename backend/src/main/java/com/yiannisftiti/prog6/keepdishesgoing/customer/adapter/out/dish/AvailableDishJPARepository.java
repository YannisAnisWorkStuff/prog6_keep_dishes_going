package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AvailableDishJPARepository extends JpaRepository<AvailableDishJPAEntity, UUID> {
    List<AvailableDishJPAEntity> findByRestaurantIdAndState(UUID restaurantId, String state);
}
