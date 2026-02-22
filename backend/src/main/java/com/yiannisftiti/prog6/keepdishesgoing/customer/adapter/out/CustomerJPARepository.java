package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerJPARepository extends JpaRepository<CustomerJPAEntity, UUID> {
    Optional<CustomerJPAEntity> findByEmail(String email);
}