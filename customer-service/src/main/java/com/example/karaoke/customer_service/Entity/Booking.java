package com.example.karaoke.customer_service.Entity;

import com.example.karaoke.customer_service.Entity.BookedRoom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Booking {
    private UUID id;
    private LocalDateTime bookDay;
    private String note;
    private List<BookedRoom> bookedRooms;

    public List<BookedRoom> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(List<BookedRoom> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getBookDay() {
        return bookDay;
    }

    public void setBookDay(LocalDateTime bookDay) {
        this.bookDay = bookDay;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
