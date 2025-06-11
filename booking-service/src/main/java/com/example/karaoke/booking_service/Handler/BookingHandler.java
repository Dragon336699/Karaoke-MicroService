package com.example.karaoke.booking_service.Handler;

import com.example.karaoke.booking_service.Entity.Booking;

public abstract class BookingHandler {
    protected  BookingHandler next;
    public BookingHandler setNext(BookingHandler next) {
        this.next = next;
        return next;
    }

    public void handle(Booking booking) {
        if (next != null) {
            next.handle(booking);
        }
    }
}
