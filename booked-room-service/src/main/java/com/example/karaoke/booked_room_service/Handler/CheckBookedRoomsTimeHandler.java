package com.example.karaoke.booked_room_service.Handler;

import com.example.karaoke.booked_room_service.Entity.BookedRoom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class CheckBookedRoomsTimeHandler extends BookedRoomHandler{
    @Override
    public void handle(BookedRoom bookedRoom) {
        if (bookedRoom.getCheckInTime().isAfter(bookedRoom.getCheckOutTime()) || bookedRoom.getCheckInTime().isBefore(LocalDateTime.now()) || bookedRoom.getCheckOutTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Time is invalid");
        }
        super.handle(bookedRoom);
    }
}
