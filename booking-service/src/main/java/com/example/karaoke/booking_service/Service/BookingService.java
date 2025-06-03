package com.example.karaoke.booking_service.Service;

import com.example.karaoke.booking_service.Entity.BookedRoom;
import com.example.karaoke.booking_service.Entity.Customer;
import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import com.example.karaoke.booking_service.Entity.Booking;
import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import com.example.karaoke.booking_service.Interface.BookedRoomClient;
import com.example.karaoke.booking_service.Interface.CustomerClient;
import com.example.karaoke.booking_service.Repository.BookingRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private BookedRoomClient bookedRoomClient;
    @Autowired
    private ModelMapper mapper;

    public UUID booking(Booking request) {
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
        try {
            Booking addBooking = new Booking();
            addBooking.setUserId(request.getUserId());
            addBooking.setBookDay(LocalDateTime.now());
            addBooking.setNote(request.getNote());
            addBooking.setCustomerId(customer.getId());
            Booking savedBooking = bookingRepository.save(addBooking);

            return savedBooking.getId();
        } catch (Exception ex) {
            return null;
        }
    }


    public List<CustomerRevenue> setCustomersBooking(List<CustomerRevenue> customersRevenue) {
        customersRevenue.forEach((customer) -> {
            List<Booking> bookings = bookingRepository.findByCustomerId(customer.getId());
            customer.setBookings(bookings);
        });

        ResponseEntity<List<CustomerRevenue>> customersRevenueRes = bookedRoomClient.setUsersBookedRoom(customersRevenue);
        return customersRevenueRes.getBody();
    }

}
