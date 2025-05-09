package com.example.karaoke.booked_room_service.Service;

import com.example.karaoke.booked_room_service.Response.CustomerRevenue;
import com.example.karaoke.booked_room_service.Entity.BookedRoom;
import com.example.karaoke.booked_room_service.Entity.Room;
import com.example.karaoke.booked_room_service.Interface.RoomClient;
import com.example.karaoke.booked_room_service.Repository.BookedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
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
        List<Room> roomsByIds = roomClient.getRoomsByListIds(request.getRoomIds());
        List<BookedRoom> addBookedRooms = roomsByIds.stream().map(room -> {
            BookedRoom bookRoom = new BookedRoom();
            bookRoom.setCheckInTime(request.getCheckInTime());
            bookRoom.setCheckOutTime(request.getCheckOutTime());
            bookRoom.setRoomId(room.getId());
            bookRoom.setPriceAtBookTime(room.getPricePerHour());
            bookRoom.setBookingId(request.getBookingId());
            return bookRoom;
        }).toList();
//        List<BookedRoom> bookedRoomMapped = addBookedRoomsRequests.stream().map(bookedRoom ->
//           mapper.map(bookedRoom, BookedRoom.class)
//        ).toList();
        bookedRepository.saveAll(addBookedRooms);
        return true;
    }

    public List<CustomerRevenue> setBookedRooms(List<CustomerRevenue> customersRevenue) {
        customersRevenue.forEach((customer) -> {
            customer.getBookings().forEach((booking) -> {
                List<BookedRoom> bookedRooms = bookedRepository.findByBookingId(booking.getId());
                List<BookedRoom> bookedRoomDtos = bookedRooms.stream()
                        .map((bookedRoom) -> mapper.map(bookedRoom, BookedRoom.class))
                        .toList();
                booking.setBookedRooms(bookedRoomDtos);
            });
        });
        return customersRevenue;
    }
}
