package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

public class Owner {

    private final OwnerId id;
    private String name;
    private String email;
    private String passwordHash;

    private RestaurantId restaurant;

    public Owner(OwnerId id, String name, String email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Owner(OwnerId id, String name, String email, String passwordHash, RestaurantId restaurant) {
        this(id, name, email, passwordHash);
        this.restaurant = restaurant;
    }

    public boolean hasRestaurant() {
        return restaurant != null;
    }

    public void registerRestaurant(RestaurantId restaurant) {
        if (this.restaurant != null) {
            throw new IllegalStateException("Owner already has a restaurant");
        }
        this.restaurant = restaurant;
        //event stuff
    }

    public OwnerId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public RestaurantId getRestaurant() {
        return restaurant;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id.id().toString() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", restaurant=" + restaurant.id().toString() +
                '}';
    }
}
