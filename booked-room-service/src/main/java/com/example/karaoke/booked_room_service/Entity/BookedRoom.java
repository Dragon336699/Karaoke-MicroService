package com.example.karaoke.booked_room_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

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
    @Transient
    private List<Room> rooms;
    private BigDecimal priceAtBookTime;
    private UUID bookingId;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
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


    public BigDecimal getPriceAtBookTime() {
        return priceAtBookTime;
    }

    public void setPriceAtBookTime(BigDecimal priceAtBookTime) {
        this.priceAtBookTime = priceAtBookTime;
    }
}
