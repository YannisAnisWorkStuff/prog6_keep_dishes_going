package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.events.*;
import com.yiannisftiti.prog6.keepdishesgoing.shared.DomainEvent;
import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import com.yiannisftiti.prog6.keepdishesgoing.shared.event.*;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

//    Restaurant name
//    Full address (street, number, postal code, city, country)
//    Contact email
//    Picture(s) (URL)
//    Type of cuisine (Italian, French, Japanese, …)
//    Default preparation time
//    Opening hours (weekly schedule)

    public static final int MAX_PUBLISHED_DISHES = 10;

    private final RestaurantId id;
    private final OwnerId ownerId;
    private String name;
    private Address address;
    private String cuisineType;
    private String email;
    private List<String> pictures;
    private double preparationTimeMinutes;
    private List<DaySchedule> schedules;
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public Restaurant(RestaurantId id, OwnerId ownerId) {
        this.id = id;
        this.ownerId=ownerId;
    }

    public Restaurant(RestaurantId id, OwnerId ownerId, String name, Address address, String cuisineType, String email, List<String> pictures, double preparationTimeMinutes, List<DaySchedule> schedules) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.email = email;
        this.pictures = pictures;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.schedules = schedules;
    }

    public void addDish(Dish dish) {
        domainEvents.add(new DishAddedEvent(this.id, dish.getId(), dish.getName()));
    }


    public void editDish(Dish dish, Dish updated) {
        dish.applyDraftChanges(updated);
        domainEvents.add(new DishEditedEvent(this.id, dish.getId()));
    }

    public void publishDish(Dish dish) {
        if (dish.isPublished()) return;
        dish.publish();
        domainEvents.add(new DishPublishedEvent(this.id.id(), dish.getId().id(), dish.getName(),dish.getPrice()));
    }

    public void unpublishDish(Dish dish) {
        dish.unpublish();
        domainEvents.add(new DishUnpublishedEvent(this.id.id(), dish.getId().id(), dish.getName()));
    }

    public void markDishOutOfStock(Dish dish) {
        dish.markOutOfStock();
        domainEvents.add(new DishOutOfStockEvent(this.id.id(), dish.getId().id(), dish.getName()));
    }

    public void markDishBackInStock(Dish dish) {
        dish.markBackInStock();
        domainEvents.add(new DishBackInStockEvent(this.id.id(), dish.getId().id(), dish.getName(),dish.getPrice()));
    }

    public void deleteDish(Dish dish) {
        domainEvents.add(new DishDeletedEvent(this.id.id(), dish.getId().id()));
    }


    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public void setPreparationTimeMinutes(double preparationTimeMinutes) {
        this.preparationTimeMinutes = preparationTimeMinutes;
    }

    public void setSchedules(List<DaySchedule> schedules) {
        this.schedules = schedules;
    }

    public RestaurantId getId() {
        return id;
    }

    public OwnerId getOwnerId() {
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

    public List<DaySchedule> getSchedules() {
        return schedules;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "preparationTimeMinutes=" + preparationTimeMinutes +
                ", email='" + email + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", address=" + address +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId.id().toString() +
                ", id=" + id.id().toString() +
                '}';
    }
}
