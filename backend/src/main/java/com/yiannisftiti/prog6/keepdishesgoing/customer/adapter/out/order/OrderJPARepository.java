package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJPARepository extends JpaRepository<OrderJPAEntity, UUID> {}
