package com.example.karaoke.booking_service.Interface;

import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "gateway-service" , contextId = "bookedRoomClient")
public interface BookedRoomClient {
    @PostMapping("/api/booked-rooms/addBookedRooms")
    ResponseEntity<String> addBookedRooms(@RequestBody Map<String, Object> request);
    @PostMapping("api/booked-rooms/setUsersBookedRoom")
    ResponseEntity<List<Map<String, Object>>> setUsersBookedRoom(@RequestBody List<Map<String, Object>> request);
}
