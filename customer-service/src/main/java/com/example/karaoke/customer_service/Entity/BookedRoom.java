package com.example.karaoke.customer_service.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BookedRoom {
    private UUID Id;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private BigDecimal priceAtBookTime;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public BigDecimal getPriceAtBookTime() {
        return priceAtBookTime;
    }

    public void setPriceAtBookTime(BigDecimal priceAtBookTime) {
        this.priceAtBookTime = priceAtBookTime;
    }
}
