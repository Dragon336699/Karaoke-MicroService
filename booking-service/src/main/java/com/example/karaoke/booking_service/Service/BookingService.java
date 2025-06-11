package com.example.karaoke.booking_service.Service;

import com.example.karaoke.booking_service.Entity.BookedRoom;
import com.example.karaoke.booking_service.Entity.Customer;
import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import com.example.karaoke.booking_service.Entity.Booking;
import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import com.example.karaoke.booking_service.Handler.BookingHandler;
import com.example.karaoke.booking_service.Handler.CheckUserHandler;
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
    private final BookingHandler chain;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookedRoomClient bookedRoomClient;
    @Autowired
    private ModelMapper mapper;

    public BookingService(CheckUserHandler checkUserHandler) {
        this.chain = checkUserHandler;
    }

    public UUID booking(Booking request) {
        chain.handle(request);
        try {
            Booking addBooking = new Booking();
            addBooking.setUserId(request.getUserId());
            addBooking.setBookDay(LocalDateTime.now());
            addBooking.setNote(request.getNote());
            addBooking.setCustomerId(request.getCustomer().getId());
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
