package dev.jawad.room_booking_api.controller;


import dev.jawad.room_booking_api.model.Booking;
import dev.jawad.room_booking_api.model.Room;
import dev.jawad.room_booking_api.payload.BookingRequest;
import dev.jawad.room_booking_api.repository.RoomRepository;
import dev.jawad.room_booking_api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping("/")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {
      try {
        Booking booking = new Booking();
        // Find the room by ID
        Room room = roomRepository.findById(bookingRequest.getRoomId())
        .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + bookingRequest.getRoomId()));

        // Validate room capacity
        if (bookingRequest.getCapacity() > room.getCapacity()) {
            // throw new IllegalArgumentException("Requested capacity exceeds room capacity.");
            return ResponseEntity.badRequest().body(Map.ofEntries(
              Map.entry("error", "Requested capacity exceeds room capacity.")
            ));
        }

        booking.setRoom(room);  // Set the found room in the booking

        // Set start and end times
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());
        return ResponseEntity.ok(bookingService.createBooking(booking));

      } catch (IllegalArgumentException e) {
          return ResponseEntity.badRequest().body(Map.ofEntries(
            Map.entry("error", e.getMessage())
          ));
      }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}/bookings")
    public ResponseEntity<Page<Booking>> listBookingsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Booking> bookings = bookingService.listBookingsByUser(userId, page, size);
        return ResponseEntity.ok(bookings);
    }
}