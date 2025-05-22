package com.example.karaoke.booking_service.Controller;

import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import com.example.karaoke.booking_service.Response.ResponseMessage;
import com.example.karaoke.booking_service.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("booking")
@Validated
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping("/addBooking")
    public ResponseEntity<?> addNewBooking( @RequestBody Map<String, Object> request) {
        boolean bookingStatus = bookingService.booking(request);
        if (bookingStatus) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Created Booking Successfully"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Created Booking Failed"));
    }

    @PostMapping("/getUsersBooking")
    public ResponseEntity<List<Map<String, Object>>> addNewBooking(@RequestBody List<Map<String, Object>> request) {
        List<Map<String, Object>> customersRevenue = bookingService.setCustomersBooking(request);
        if (!customersRevenue.isEmpty()) {
            return ResponseEntity.ok(customersRevenue);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
