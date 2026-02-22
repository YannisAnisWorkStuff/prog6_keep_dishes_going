package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.restaurant;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurant")
public class RestaurantJPAEntity {

    @Id
    private UUID uuid;

    @Column(nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    private String cuisineType;

    @Column(nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "restaurant_pictures", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "picture_url")
    private List<String> pictures;

    private double preparationTimeMinutes;

    @Column(length = 1000)
    private String schedulesJson;

    public RestaurantJPAEntity() {
        this.uuid = UUID.randomUUID();
    }

    public RestaurantJPAEntity(UUID uuid, UUID ownerId, String name, Address address, String cuisineType, String email,
                               List<String> pictures, double preparationTimeMinutes, String schedulesJson) {
        this.uuid = uuid;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.email = email;
        this.pictures = pictures;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.schedulesJson = schedulesJson;
    }

    public RestaurantJPAEntity(UUID ownerId, String name, Address address, String cuisineType, String email,
                               List<String> pictures, double preparationTimeMinutes, String schedulesJson) {
        this();
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.email = email;
        this.pictures = pictures;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.schedulesJson = schedulesJson;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public double getPreparationTimeMinutes() {
        return preparationTimeMinutes;
    }

    public String getSchedulesJson() {
        return schedulesJson;
    }
}