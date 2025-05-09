package com.example.karaoke.booked_room_service.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Booking {
    private UUID id;
    private LocalDateTime bookDay;
    private String note;
    private List<BookedRoom> bookedRooms;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public List<BookedRoom> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(List<BookedRoom> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }
}
