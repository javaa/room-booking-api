package dev.jawad.room_booking_api.payload;

import java.time.LocalDateTime;

public class RoomAvailabilityRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Getters and Setters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}