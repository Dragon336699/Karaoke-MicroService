package com.example.karaoke.booked_room_service.Controller;

import com.example.karaoke.booked_room_service.Entity.BookedRoom;
import com.example.karaoke.booked_room_service.Entity.CustomerRevenue;
import com.example.karaoke.booked_room_service.Entity.Room;
import com.example.karaoke.booked_room_service.Response.ResponseMessage;
import com.example.karaoke.booked_room_service.Service.BookedRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("booked-rooms")
public class BookedRoomController {
    @Autowired
    private BookedRoomService bookedRoomService;

    @GetMapping("/getAvailableRooms")
    public ResponseEntity<List<Room>> getAvailableRooms(@RequestParam OffsetDateTime checkInTime, @RequestParam OffsetDateTime checkOutTime) {
        if (checkInTime.isAfter(checkOutTime) || checkInTime.isBefore(OffsetDateTime.now()) || checkOutTime.isBefore(OffsetDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(bookedRoomService.getAvailableRooms(checkInTime, checkOutTime));
    }

    @PostMapping("/addBookedRooms")
    public ResponseEntity<?> addBookedRooms(@RequestBody BookedRoom request) {
        boolean addBookedRoomsStatus = bookedRoomService.addBookedRooms(request);
        if (addBookedRoomsStatus) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Created Booking Successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Created Booking Failed"));
    }

    @PostMapping("/setUsersBookedRoom")
    public ResponseEntity<List<CustomerRevenue>> addNewBooking(@RequestBody List<CustomerRevenue> request) {
        List<CustomerRevenue> customersRevenue = bookedRoomService.setBookedRooms(request);
        if (!customersRevenue.isEmpty()) {
            return ResponseEntity.ok(customersRevenue);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
