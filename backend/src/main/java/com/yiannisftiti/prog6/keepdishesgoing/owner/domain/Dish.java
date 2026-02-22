package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish {

    public enum Type {
        STARTER, MAIN, DESSERT
    }

    public enum State {
        DRAFT, PUBLISHED, OUT_OF_STOCK, UNPUBLISHED
    }

    private final DishId id;
    private final RestaurantId restaurantId;
    private String name;
    private Type dishType;
    private String description;
    private List<String> foodTags;
    private String picURL;
    private Dish draftVersion;
    private BigDecimal price;
    private State state;

    public Dish(RestaurantId restaurantId, String name, Type type, String description,
                BigDecimal price, String pictureUrl, List<String> tags) {
        this.id = DishId.create();
        this.restaurantId = restaurantId;
        this.name = name;
        this.dishType = type;
        this.description = description;
        this.price = price;
        this.picURL = pictureUrl;
        this.foodTags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.state = State.DRAFT;
    }

    public Dish(DishId id, RestaurantId restaurantId, String name, Type type, String description,
                BigDecimal price, String pictureUrl, List<String> tags, State state) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.dishType = type;
        this.description = description;
        this.price = price;
        this.picURL = pictureUrl;
        this.foodTags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.state = state;
    }

    public void applyDraftChanges(Dish updated) {
        this.draftVersion = updated;
    }

    public void publish() {
        if (draftVersion != null) copyFrom(draftVersion);
        this.state = State.PUBLISHED;
        this.draftVersion = null;
    }

    public void unpublish() {
        this.state = State.UNPUBLISHED;
    }

    public void markOutOfStock() {
        if (state != State.PUBLISHED)
            throw new IllegalStateException("Only published dishes can be out of stock");
        this.state = State.OUT_OF_STOCK;
    }

    public void markBackInStock() {
        if (state != State.OUT_OF_STOCK)
            throw new IllegalStateException("Dish is not out of stock");
        this.state = State.PUBLISHED;
    }

    private void copyFrom(Dish dish) {
        this.name = dish.name;
        this.dishType = dish.dishType;
        this.description = dish.description;
        this.price = dish.price;
        this.picURL = dish.picURL;
        this.foodTags = new ArrayList<>(dish.foodTags);
    }

    public boolean hasDraftVersion() {
        return this.draftVersion != null;
    }

    public DishId getId() { return id; }
    public RestaurantId getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public Type getType() { return dishType; }
    public String getDescription() { return description; }
    public List<String> getFoodTags() { return foodTags; }
    public BigDecimal getPrice() { return price; }
    public String getPictureUrl() { return picURL; }
    public State getState() { return state; }

    public boolean isPublished() { return state == State.PUBLISHED; }
    public boolean isDraft() { return state == State.DRAFT; }
    public boolean isOutOfStock() { return state == State.OUT_OF_STOCK; }

    public void setPicture(String url) { this.picURL = url; }

    public void setFoodTags(String... tags) {
        this.foodTags = new ArrayList<>(Arrays.asList(tags));
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id.id() +
                ", restaurantId=" + restaurantId.id() +
                ", name='" + name + '\'' +
                ", type=" + dishType.name() +
                ", state=" + state +
                ", price=" + price +
                '}';
    }
}