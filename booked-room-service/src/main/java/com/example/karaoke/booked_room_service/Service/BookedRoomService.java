package com.example.karaoke.booked_room_service.Service;

import com.example.karaoke.booked_room_service.Entity.Booking;
import com.example.karaoke.booked_room_service.Entity.CustomerRevenue;
import com.example.karaoke.booked_room_service.Entity.BookedRoom;
import com.example.karaoke.booked_room_service.Entity.Room;
import com.example.karaoke.booked_room_service.Interface.RoomClient;
import com.example.karaoke.booked_room_service.Repository.BookedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.HashMap;
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

    public boolean addBookedRooms(Map<String, Object> request) {
        List<String> idStrings = (List<String>) request.get("roomIds");
        String checkInTimeString = request.get("checkInTime").toString();
        String checkOutTimeString = request.get("checkOutTime").toString();
        UUID bookingId = UUID.fromString(request.get("bookingId").toString());

        List<UUID> roomIds = idStrings.stream()
                .map(UUID::fromString)
                .toList();

        List<Room> roomsByIds = roomClient.getRoomsByListIds(roomIds);
        List<BookedRoom> addBookedRooms = roomsByIds.stream().map(room -> {
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

    public List<Map<String, Object>> setBookedRooms(List<Map<String, Object>> customersRevenue) {
        customersRevenue.forEach((customer) -> {
            List<Map<String, Object>> customerBookings = (List<Map<String, Object>>) customer.get("bookings");
            if (!customerBookings.isEmpty()) {
                customerBookings.forEach((booking) -> {
                    UUID bookingId = UUID.fromString(booking.get("id").toString());
                    List<BookedRoom> bookedRooms = bookedRepository.findByBookingId(bookingId);
                    List<Map<String, Object>> bookedRoomDtos = bookedRooms.stream()
                            .map(bookedRoom -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("id", bookedRoom.getId());
                                map.put("checkInTime", bookedRoom.getCheckInTime());
                                map.put("checkOutTime", bookedRoom.getCheckOutTime());
                                map.put("roomId", bookedRoom.getRoomId());
                                map.put("priceAtBookTime", bookedRoom.getPriceAtBookTime());
                                map.put("bookingId", bookedRoom.getBookingId());
                                return map;
                            })
                            .toList();
                    booking.put("bookedRooms", bookedRoomDtos);
                });
            }
        });
        return customersRevenue;
    }
}
