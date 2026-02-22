package com.yiannisftiti.prog6.keepdishesgoing.customer.domain;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;

public class Customer {
    private final CustomerId id;
    private String name;
    private String email;
    private Address address;
    private String encryptedPassword;

    public Customer(CustomerId id, String name, String email, String encryptedPassword, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address=address;
        this.encryptedPassword = encryptedPassword; // i don't even need this since i'm signing using keycloak, but I'll keep it to keep things uniformal with Owner
    }

    public Customer(String name,  String email, String encryptedPassword, Address address) {
        this.id=CustomerId.create();
        this.name = name;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.address=address;
    }

    public CustomerId getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public Address getAddress() { return address; }

    public void changeAddress(Address newAddress) {
        this.address = newAddress;
    }
}
