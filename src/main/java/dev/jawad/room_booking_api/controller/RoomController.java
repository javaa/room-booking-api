package dev.jawad.room_booking_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import dev.jawad.room_booking_api.model.Room;
import dev.jawad.room_booking_api.payload.RoomAvailabilityRequest;
import dev.jawad.room_booking_api.service.RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Room>> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Room> rooms = roomService.getAllRooms(page, size);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Room updatedRoom = roomService.updateRoom(id, roomDetails);
        return updatedRoom != null ? ResponseEntity.ok(updatedRoom) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/available")
    public ResponseEntity<?> getAvailableRooms(@RequestBody RoomAvailabilityRequest request) {
        // Set default values for page and size if not provided
        int page = request.getPage() > 0 ? request.getPage() : 0; // Default to 0 if page is not positive
        int size = request.getSize() > 0 ? request.getSize() : 10; // Default to 10 if size is not positive

        // Validate that size is at least 1
        if (size < 1) {
            return ResponseEntity.badRequest().body(Map.of("error", "Page size must be at least one."));
        }

        // Update the request object with validated values
        request.setPage(page);
        request.setSize(size);

        Page<Room> availableRooms = roomService.findAvailableRooms(request);

        if (availableRooms.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no rooms are available
        }

        return ResponseEntity.ok(availableRooms);
    }
}
