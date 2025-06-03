package com.example.karaoke.booked_room_service.Service;

import com.example.karaoke.booked_room_service.Entity.CustomerRevenue;
import com.example.karaoke.booked_room_service.Entity.BookedRoom;
import com.example.karaoke.booked_room_service.Entity.Room;
import com.example.karaoke.booked_room_service.Interface.RoomClient;
import com.example.karaoke.booked_room_service.Repository.BookedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookedRoomService {
    @Autowired
    private BookedRepository bookedRepository;
    @Autowired
    private RoomClient roomClient;
    @Autowired
    private ModelMapper mapper;

    public List<Room> getAvailableRooms(OffsetDateTime checkInTime, OffsetDateTime checkOutTime) {
        ZonedDateTime vnCheckInTime = checkInTime.atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime vnCheckInTimeLocal = vnCheckInTime.toLocalDateTime();
        ZonedDateTime vnCheckOutTime = checkOutTime.atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime vnCheckOutTimeLocal = vnCheckOutTime.toLocalDateTime();
        List<Room> rooms = roomClient.getAllRooms();
        List<UUID> unavailableRoomIds = bookedRepository.getRoomIdsUnavailable(vnCheckInTimeLocal,vnCheckOutTimeLocal);
        List<Room> availableRooms = rooms.stream()
                .filter(room -> !unavailableRoomIds.contains(room.getId()))
                .toList();

        return availableRooms;
    }

    public boolean addBookedRooms(BookedRoom request) {
        List<BookedRoom> addBookedRooms = request.getRooms().stream().map(room -> {
            BookedRoom bookRoom = new BookedRoom();
            bookRoom.setCheckInTime(LocalDateTime.parse(checkInTimeString));
            bookRoom.setCheckOutTime(LocalDateTime.parse(checkOutTimeString));
            bookRoom.setRoomId(room.getId());
            bookRoom.setPriceAtBookTime(room.getPricePerHour());
            bookRoom.setBookingId(bookingId);
            return bookRoom;
        }).toList();
        bookedRepository.saveAll(addBookedRooms);
        return true;
    }

    public List<CustomerRevenue> setBookedRooms(List<CustomerRevenue> customersRevenue) {
        customersRevenue.forEach((customer) -> {
            customer.getBookings().forEach((booking) -> {
                List<BookedRoom> bookedRooms = bookedRepository.findByBookingId(booking.getId());
                booking.setBookedRooms(bookedRooms);
            });
        });
        return customersRevenue;
    }
}
