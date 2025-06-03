package com.example.karaoke.booking_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Booking {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDateTime bookDay;
    private String note;
    private UUID customerId;
    private UUID userId;
    @Transient
    private Customer customer;
    @Transient
    private List<BookedRoom> bookedRooms;

    public void setId(UUID id) {
        this.id = id;
    }

    public List<BookedRoom> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(List<BookedRoom> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getBookDay() {
        return bookDay;
    }

    public void setBookDay(LocalDateTime bookDay) {
        this.bookDay = bookDay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
