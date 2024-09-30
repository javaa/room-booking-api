package dev.jawad.room_booking_api.service;

import dev.jawad.room_booking_api.model.Booking;
import dev.jawad.room_booking_api.model.Room;
import dev.jawad.room_booking_api.payload.RoomAvailabilityRequest;
import dev.jawad.room_booking_api.repository.BookingRepository;
import dev.jawad.room_booking_api.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Room createRoom(Room room) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
          return roomRepository.save(room);
        } else {
          throw new RuntimeException("You do not have permission to create a room. Only Admin is allowed to create rooms");
        }
    }

    public Page<Room> getAllRooms(int page, int size) {
        return roomRepository.findAll(PageRequest.of(page, size));
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    public Room updateRoom(Long roomId, Room roomDetails) {
        Room existingRoom = getRoomById(roomId);
        if (existingRoom != null) {
            existingRoom.setName(roomDetails.getName());
            existingRoom.setCapacity(roomDetails.getCapacity());
            existingRoom.setAvailable(roomDetails.isAvailable());
            return roomRepository.save(existingRoom);
        }
        return null; // or throw an exception
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    public List<Room> findAvailableRooms(RoomAvailabilityRequest request) {
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();

        // Get all rooms
        List<Room> allRooms = roomRepository.findAll();

        // Filter rooms based on availability
        return allRooms.stream()
                .filter(room -> room.isAvailable() && isRoomAvailable(room.getId(), startTime, endTime))
                .collect(Collectors.toList());
    }

    private boolean isRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> existingBookings = bookingRepository.findByRoomId(roomId);

        for (Booking existingBooking : existingBookings) {
            // Check for overlapping bookings
            if ((startTime.isBefore(existingBooking.getEndTime()) && endTime.isAfter(existingBooking.getStartTime()))) {
                return false; // Room is not available due to overlapping bookings
            }
        }

        return true; // Room is available
    }
}
