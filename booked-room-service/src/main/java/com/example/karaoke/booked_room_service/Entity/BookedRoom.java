package com.example.karaoke.booked_room_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class BookedRoom {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private UUID roomId;
    private List<UUID> roomIds;
    private BigDecimal priceAtBookTime;
    private UUID bookingId;

    public List<UUID> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<UUID> roomIds) {
        this.roomIds = roomIds;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getId() {
        return id;
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

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public BigDecimal getPriceAtBookTime() {
        return priceAtBookTime;
    }

    public void setPriceAtBookTime(BigDecimal priceAtBookTime) {
        this.priceAtBookTime = priceAtBookTime;
    }
}
