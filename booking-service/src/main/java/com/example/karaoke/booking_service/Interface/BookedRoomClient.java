package com.example.karaoke.booking_service.Interface;

import com.example.karaoke.booking_service.Entity.BookedRoom;
import com.example.karaoke.booking_service.Response.CustomerRevenue;
import com.example.karaoke.booking_service.Request.BookRoomsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "gateway-service" , contextId = "bookedRoomClient")
public interface BookedRoomClient {
    @PostMapping("/api/booked-rooms/addBookedRooms")
    ResponseEntity<String> addBookedRooms(@RequestBody BookedRoom request);
    @PostMapping("api/booked-rooms/setUsersBookedRoom")
    ResponseEntity<List<CustomerRevenue>> setUsersBookedRoom(@RequestBody List<CustomerRevenue> request);
}
