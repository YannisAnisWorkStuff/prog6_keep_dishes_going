package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.out.restaurant;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "dishes")
public class DishJPAEntity {

    @Id
    private UUID id;

    private UUID restaurantId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String name;
    private String type;
    private String description;
    private String tags;
    private BigDecimal price;
    private String pictureUrl;
    private String state;

    public DishJPAEntity() {}

    public DishJPAEntity(UUID id, UUID restaurantId, String name, String type, String description,
                         String tags, BigDecimal price, String pictureUrl, String state) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.tags = tags;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.state = state;
    }

}
