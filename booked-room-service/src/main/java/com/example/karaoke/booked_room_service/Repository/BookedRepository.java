package com.example.karaoke.booked_room_service.Repository;

import com.example.karaoke.booked_room_service.Entity.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookedRepository extends JpaRepository<BookedRoom, UUID> {
    @Query("""
            SELECT br.roomId FROM BookedRoom br
            WHERE (br.checkInTime <= :checkInTime AND br.checkOutTime >= :checkInTime) OR (br.checkInTime <= :checkOutTime AND br.checkOutTime >= :checkOutTime)
        """)
    List<UUID> getRoomIdsUnavailable(@Param("checkInTime")LocalDateTime checkInTime, @Param("checkOutTime") LocalDateTime checkOutTime);

    List<BookedRoom> findByBookingId(UUID bookingId);
}
