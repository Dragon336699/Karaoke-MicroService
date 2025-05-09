package com.example.karaoke.room_service.Repository;

import com.example.karaoke.room_service.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
