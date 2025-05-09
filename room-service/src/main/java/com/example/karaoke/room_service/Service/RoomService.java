package com.example.karaoke.room_service.Service;

import com.example.karaoke.room_service.Entity.Room;
import com.example.karaoke.room_service.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByListIds(List<UUID> roomIds) {
        return roomRepository.findAllById(roomIds);
    }
}
