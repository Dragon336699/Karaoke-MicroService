package com.example.karaoke.booked_room_service.Handler;

import com.example.karaoke.booked_room_service.Entity.BookedRoom;

public abstract class BookedRoomHandler {
    protected BookedRoomHandler next;

    public BookedRoomHandler setNext(BookedRoomHandler next) {
        this.next = next;
        return next;
    }

    public void handle (BookedRoom bookedRoom) {
        if (next != null) {
            next.handle(bookedRoom);
        }
    }
}
