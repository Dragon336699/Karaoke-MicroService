package com.example.karaoke.booking_service.Controller;

import com.example.karaoke.booking_service.Entity.Booking;
import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import com.example.karaoke.booking_service.Response.ResponseMessage;
import com.example.karaoke.booking_service.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("booking")
@Validated
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping("/addBooking")
    public ResponseEntity<?> addNewBooking(@Valid @RequestBody Booking request) {
        UUID bookId = bookingService.booking(request);
        if (bookId != null) {
            Map<String, UUID> response = new HashMap<>();
            response.put("id", bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Created Booking Failed"));
    }

    @PostMapping("/getUsersBooking")
    public ResponseEntity<List<CustomerRevenue>> addNewBooking(@RequestBody List<CustomerRevenue> request) {
        List<CustomerRevenue> customersRevenue = bookingService.setCustomersBooking(request);
        if (!customersRevenue.isEmpty()) {
            return ResponseEntity.ok(customersRevenue);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
