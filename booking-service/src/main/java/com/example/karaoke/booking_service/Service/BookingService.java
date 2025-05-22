package com.example.karaoke.booking_service.Service;

import com.example.karaoke.booking_service.Entity.BookedRoom;
import com.example.karaoke.booking_service.Entity.Customer;
import com.example.karaoke.booking_service.Entity.Booking;
import com.example.karaoke.booking_service.Entity.CustomerRevenue;
import com.example.karaoke.booking_service.Interface.BookedRoomClient;
import com.example.karaoke.booking_service.Interface.CustomerClient;
import com.example.karaoke.booking_service.Repository.BookingRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public boolean booking(Map<String, Object> request) {
        List<String> idsString = (List<String>)request.get("roomIds");
        List<UUID> roomIds = idsString.stream()
                .map(UUID::fromString)
                .toList();
        String checkInTimeString = request.get("checkInDate").toString();
        OffsetDateTime checkInTime = OffsetDateTime.parse(checkInTimeString);
        String checkOutTimeString = request.get("checkOutDate").toString();
        OffsetDateTime checkOutTime = OffsetDateTime.parse(checkOutTimeString);
        String userIdString = request.get("userId").toString();
        UUID userId = UUID.fromString(userIdString);
        String customerPhoneNumber = request.get("customerPhoneNumber").toString();
        String note = request.get("note").toString();
        String fullName = request.get("fullName").toString();

        Customer customer = new Customer();
        try {
            ResponseEntity<Customer> customerResponse = customerClient.getCustomer(customerPhoneNumber);
            customer = (Customer) customerResponse.getBody();
        } catch (FeignException.NotFound ex) {
            Customer addCustomerRequest = new Customer();
            addCustomerRequest.setFullName(fullName);
            addCustomerRequest.setPhoneNumber(customerPhoneNumber);
            customerClient.addCustomer(addCustomerRequest);
            ResponseEntity<Customer> customerResponse = customerClient.getCustomer(customerPhoneNumber);
            customer = (Customer) customerResponse.getBody();
        }

        Booking addBooking = new Booking();
        addBooking.setUserId(userId);
        addBooking.setBookDay(LocalDateTime.now());
        addBooking.setNote(note);
        addBooking.setCustomerId(customer.getId());

        Booking savedBooking = bookingRepository.save(addBooking);

        ZonedDateTime vnCheckInTime = checkInTime.atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime vnCheckInTimeLocal = vnCheckInTime.toLocalDateTime();
        ZonedDateTime vnCheckOutTime = checkOutTime.atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime vnCheckOutTimeLocal = vnCheckOutTime.toLocalDateTime();

        BookedRoom bookedRoom = new BookedRoom();
        Map<String, Object> body = new HashMap<>();
        body.put("bookingId", savedBooking.getId());
        body.put("roomIds", roomIds);
        body.put("checkInTime", vnCheckInTimeLocal);
        body.put("checkOutTime", vnCheckOutTimeLocal);

        ResponseEntity<String> response = bookedRoomClient.addBookedRooms(body);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return true;
        }
        return false;
    }

    public List<CustomerRevenue> setCustomersBooking(List<CustomerRevenue> customersRevenue) {
        customersRevenue.forEach((customer) -> {
            List<Booking> bookings = bookingRepository.findByCustomerId(customer.getId());
//            List<Booking> bookingsResponse = bookings.stream()
//                    .map((booking) -> mapper.map(booking, BookingResponse.class))
//                    .toList();
            customer.setBookings(bookings);
        });

        ResponseEntity<List<CustomerRevenue>> customersRevenueRes = bookedRoomClient.setUsersBookedRoom(customersRevenue);
        return customersRevenueRes.getBody();
    }

}
