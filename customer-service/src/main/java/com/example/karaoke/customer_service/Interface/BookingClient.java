package com.example.karaoke.customer_service.Interface;

import com.example.karaoke.customer_service.Entity.CustomerRevenue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "gateway-service", contextId="bookingClient")
public interface BookingClient {
    @PostMapping("/api/booking/getUsersBooking")
    ResponseEntity<List<Map<String, Object>>> getUsersBooking(@RequestBody List<Map<String, Object>> request);
}
