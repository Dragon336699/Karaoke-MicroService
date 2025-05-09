package com.example.karaoke.booked_room_service.Interface;

import com.example.karaoke.booked_room_service.Entity.Room;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "gateway-service")
public interface RoomClient {
    @GetMapping("/api/rooms/getAll")
    List<Room> getAllRooms();

    @PostMapping("/api/rooms/getByListIds")
    List<Room> getRoomsByListIds(@RequestBody List<UUID> roomIds) ;
}
