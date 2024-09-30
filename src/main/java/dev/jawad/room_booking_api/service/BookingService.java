package dev.jawad.room_booking_api.service;

import dev.jawad.room_booking_api.model.User;
import dev.jawad.room_booking_api.model.Booking;
import dev.jawad.room_booking_api.security.UserPrincipal;
import dev.jawad.room_booking_api.repository.BookingRepository;
import dev.jawad.room_booking_api.repository.RoomRepository;
import dev.jawad.room_booking_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
  private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(Booking booking) {
        // Validate start time and end time
        if (booking.getStartTime().isAfter(booking.getEndTime())) {
          throw new IllegalArgumentException("Start time must be before end time.");
        }
        // Check room booking for past times.
        if (booking.getStartTime().isBefore(LocalDateTime.now())) {
          throw new IllegalArgumentException("Cannot book a room for past times.");
        }

        // Check if the room is available during the requested time
        if (!isRoomAvailable(booking.getRoom().getId(), booking.getStartTime(), booking.getEndTime())) {
          throw new IllegalArgumentException("Room is not available during the requested time.");
        }

        // Get currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId(); // Assuming UserPrincipal has getId()

        // Set the user for the booking
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        booking.setUser(user); // Set the authenticated user to the booking

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public Page<Booking> listBookingsByUser(Long userId, int page, int size) {
        return bookingRepository.findByUser_Id(userId, PageRequest.of(page, size));
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