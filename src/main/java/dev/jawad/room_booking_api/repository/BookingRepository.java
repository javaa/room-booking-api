package dev.jawad.room_booking_api.repository;

import dev.jawad.room_booking_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  Page<Booking> findByUser_Id(Long userId, Pageable pageable);
  List<Booking> findByRoomId(Long roomId); // Method to find bookings by room ID
}
