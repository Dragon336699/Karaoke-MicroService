package com.example.karaoke.room_service.Controller;

import com.example.karaoke.room_service.Entity.Room;
import com.example.karaoke.room_service.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @GetMapping("/getAll")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping("/getByListIds")
    public ResponseEntity<List<Room>> getRoomsByListIds(@RequestBody List<UUID> roomIds) {
        return ResponseEntity.ok(roomService.getRoomsByListIds(roomIds));
    }
}
