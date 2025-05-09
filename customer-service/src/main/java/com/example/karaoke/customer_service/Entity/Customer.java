package com.example.karaoke.customer_service.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;
    private String fullName;
    @Column(unique = true)
    private String phoneNumber;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
