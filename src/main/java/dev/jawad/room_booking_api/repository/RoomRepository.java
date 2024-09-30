package dev.jawad.room_booking_api.repository;

import dev.jawad.room_booking_api.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}