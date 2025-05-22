package com.example.karaoke.booking_service.Interface;

import com.example.karaoke.booking_service.Entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gateway-service" , contextId = "customerClient")
public interface CustomerClient {
    @GetMapping("/api/customers/getCustomer")
    ResponseEntity<Customer> getCustomer(@RequestParam String phoneNumber);
    @PostMapping("/api/customers/addCustomer")
    ResponseEntity<String> addCustomer(@RequestBody Customer request);
}
