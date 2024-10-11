package dev.jawad.room_booking_api.service;

import dev.jawad.room_booking_api.model.User;
import dev.jawad.room_booking_api.errors.Errors;
import dev.jawad.room_booking_api.exception.ApplicationException;
import dev.jawad.room_booking_api.model.Booking;
import dev.jawad.room_booking_api.model.Room;
import dev.jawad.room_booking_api.security.UserPrincipal;
import dev.jawad.room_booking_api.repository.BookingRepository;
import dev.jawad.room_booking_api.repository.RoomRepository;
import dev.jawad.room_booking_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(Booking booking) {
        // Validate start time and end time
        if (booking.getStartTime().isAfter(booking.getEndTime())) {
          // throw new IllegalArgumentException("Start time must be before end time.");
          throw new ApplicationException(Errors.START_TIME_AFTER_END_TIME);
        }
        // Check room booking for past times.
        if (booking.getStartTime().isBefore(LocalDateTime.now())) {
          // throw new IllegalArgumentException("Cannot book a room for past times.");
          throw new ApplicationException(Errors.PAST_BOOKING_TIME);
        }

        // Check if the room is available during the requested time
        if (!isRoomAvailable(booking.getRoom().getId(), booking.getStartTime(), booking.getEndTime())) {
          // throw new IllegalArgumentException("Room is not available during the requested time.");
          throw new ApplicationException(Errors.ROOM_NOT_AVAILABLE);
        }

        // Get currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        // Set the user for the booking
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(Errors.USER_NOT_FOUND, Map.of("id", userId)));

        booking.setUser(user); // Set the authenticated user to the booking

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long bookingId) {
      return bookingRepository.findById(bookingId).orElse(null);
    }

    public Booking updateBooking(Long bookingId, Booking bookingDetails) {
      Booking existingBooking = getBookingById(bookingId);
      Long roomId = bookingDetails.getRoom().getId();
      // Check if the room from bookingDetails is not null
      // if (bookingDetails.getRoomId() == null) {
      //     throw new IllegalArgumentException("Room cannot be null");
      // }

      // // Ensure the room exists in the database
      Room room = roomRepository.findById(roomId)
              .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + bookingDetails.getRoom().getId()));

      // Update the existing booking with new details
      if (existingBooking != null) {
          existingBooking.setRoom(room); // Set the Room object here
          existingBooking.setStartTime(bookingDetails.getStartTime());
          existingBooking.setEndTime(bookingDetails.getEndTime());
          return bookingRepository.save(existingBooking);
      }

      throw new ApplicationException(Errors.BOOKING_NOT_FOUND, Map.of("id", bookingId));
    }

    public Page<Booking> getAllBookings(int page, int size) {
        return bookingRepository.findAll(PageRequest.of(page, size));
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