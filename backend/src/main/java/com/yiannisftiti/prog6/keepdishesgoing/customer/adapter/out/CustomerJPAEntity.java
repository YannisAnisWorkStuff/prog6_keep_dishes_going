package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "customer")
public class CustomerJPAEntity {

    @Id
    private UUID id;

    private String name;
    private String email;
    private String encryptedPassword;

    @Embedded
    private Address address;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEncryptedPassword() { return encryptedPassword; }
    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    
}