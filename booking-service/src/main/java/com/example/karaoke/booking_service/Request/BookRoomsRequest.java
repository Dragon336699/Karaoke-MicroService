package com.example.karaoke.booking_service.Request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class    BookRoomsRequest {
    private List<UUID> roomIds;
    private UUID bookingId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    public BookRoomsRequest(UUID bookingId, List<UUID> roomIds, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.bookingId = bookingId;
        this.roomIds = roomIds;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
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

    public List<UUID> getRoomIds() {
        return roomIds;
    }

    public void setRoomId(List<UUID> roomIds) {
        this.roomIds = roomIds;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }
}

