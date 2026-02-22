package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.owner;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "owner")
public class OwnerJPAEntity {

    @Id
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    public OwnerJPAEntity() {
        this.uuid = UUID.randomUUID();
    }

    public OwnerJPAEntity(String name, String email, String passwordHash) {
        this();
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public OwnerJPAEntity(UUID uuid, String name, String email, String passwordHash, UUID restaurantId) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.restaurantId = restaurantId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
