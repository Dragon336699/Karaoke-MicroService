package com.example.karaoke.booking_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;
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
}
