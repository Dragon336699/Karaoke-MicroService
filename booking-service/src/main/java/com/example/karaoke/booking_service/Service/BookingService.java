package com.example.karaoke.booking_service.Service;

import com.example.karaoke.booking_service.Entity.BookedRoom;
import com.example.karaoke.booking_service.Entity.Customer;
import com.example.karaoke.booking_service.Response.BookingResponse;
import com.example.karaoke.booking_service.Response.CustomerRevenue;
import com.example.karaoke.booking_service.Entity.Booking;
import com.example.karaoke.booking_service.Interface.BookedRoomClient;
import com.example.karaoke.booking_service.Interface.CustomerClient;
import com.example.karaoke.booking_service.Repository.BookingRepository;
import com.example.karaoke.booking_service.Request.AddCustomerRequest;
import com.example.karaoke.booking_service.Request.BookRoomsRequest;
import com.example.karaoke.booking_service.Request.BookingRequest;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

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

    public boolean booking(BookingRequest request) {
        Customer customer = new Customer();
        try {
            ResponseEntity<Customer> customerResponse = customerClient.getCustomer(request.getCustomerPhoneNumber());
            customer = (Customer) customerResponse.getBody();
        } catch (FeignException.NotFound ex) {
            AddCustomerRequest addCustomerRequest = new AddCustomerRequest();
            addCustomerRequest.setFullName(request.getFullName());
            addCustomerRequest.setPhoneNumber(request.getCustomerPhoneNumber());
            customerClient.addCustomer(addCustomerRequest);
            ResponseEntity<Customer> customerResponse = customerClient.getCustomer(request.getCustomerPhoneNumber());
            customer = (Customer) customerResponse.getBody();
        }

        Booking addBooking = new Booking();
        addBooking.setUserId(request.getUserId());
        addBooking.setBookDay(LocalDateTime.now());
        addBooking.setNote(request.getNote());
        addBooking.setCustomerId(customer.getId());

        Booking savedBooking = bookingRepository.save(addBooking);

        ZonedDateTime vnCheckInTime = request.getCheckInDate().atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime vnCheckInTimeLocal = vnCheckInTime.toLocalDateTime();
        ZonedDateTime vnCheckOutTime = request.getCheckOutDate().atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime vnCheckOutTimeLocal = vnCheckOutTime.toLocalDateTime();

        BookedRoom bookedRoomReq = new BookedRoom();
        bookedRoomReq.setBookingId(savedBooking.getId());
        bookedRoomReq.setRoomIds(request.getRoomIds());
        bookedRoomReq.setCheckInTime(vnCheckInTimeLocal);
        bookedRoomReq.setCheckOutTime(vnCheckOutTimeLocal);

        ResponseEntity<String> response = bookedRoomClient.addBookedRooms(bookedRoomReq);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return true;
        }
        return false;
    }

    public List<CustomerRevenue> setCustomersBooking(List<CustomerRevenue> customersRevenue) {
        customersRevenue.forEach((customer) -> {
            List<Booking> bookings = bookingRepository.findByCustomerId(customer.getId());
            List<BookingResponse> bookingsResponse = bookings.stream()
                    .map((booking) -> mapper.map(booking, BookingResponse.class))
                    .toList();
            customer.setBookings(bookingsResponse);
        });

        ResponseEntity<List<CustomerRevenue>> customersRevenueRes = bookedRoomClient.setUsersBookedRoom(customersRevenue);
        return customersRevenueRes.getBody();
    }

}
