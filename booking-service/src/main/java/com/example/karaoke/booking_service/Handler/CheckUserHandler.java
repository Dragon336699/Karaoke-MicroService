package com.example.karaoke.booking_service.Handler;

import com.example.karaoke.booking_service.Entity.Booking;
import com.example.karaoke.booking_service.Entity.Customer;
import com.example.karaoke.booking_service.Interface.CustomerClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CheckUserHandler extends BookingHandler{
    @Autowired
    private CustomerClient customerClient;

    @Override
    public void handle(Booking request) {
        Customer customer;
        try {
            ResponseEntity<Customer> customerResponse = customerClient.getCustomer(request.getCustomer().getPhoneNumber());
            customer = customerResponse.getBody();
        } catch (FeignException.NotFound ex) {
            Customer addCustomerRequest = new Customer();
            addCustomerRequest.setFullName(request.getCustomer().getFullName());
            addCustomerRequest.setPhoneNumber(request.getCustomer().getPhoneNumber());
            customerClient.addCustomer(addCustomerRequest);
            ResponseEntity<Customer> customerResponse = customerClient.getCustomer(request.getCustomer().getPhoneNumber());
            customer = customerResponse.getBody();
        }
        request.setCustomer(customer);
        super.handle(request);
    }
}
