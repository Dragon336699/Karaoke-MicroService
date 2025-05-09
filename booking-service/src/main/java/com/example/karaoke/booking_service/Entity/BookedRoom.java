package com.example.karaoke.booking_service.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BookedRoom {
    private UUID Id;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private BigDecimal priceAtBookTime;
    private List<UUID> roomIds;
    private UUID bookingId;

    public List<UUID> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<UUID> roomIds) {
        this.roomIds = roomIds;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public BigDecimal getPriceAtBookTime() {
        return priceAtBookTime;
    }

    public void setPriceAtBookTime(BigDecimal priceAtBookTime) {
        this.priceAtBookTime = priceAtBookTime;
    }
}
