package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomingOrderJPARepository extends JpaRepository<IncomingOrderJPAEntity, UUID> {
    Optional<IncomingOrderJPAEntity> findByRestaurantIdAndOrderId(UUID restaurantId, UUID orderId);
    List<IncomingOrderJPAEntity> findByStatus(String status);
}