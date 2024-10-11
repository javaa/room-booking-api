package dev.jawad.room_booking_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;
    private boolean availability;

    public Room() {
    }

    // Constructor with parameters
    public Room(Long id) {
        this.id = id;
    }

    // Getter for roomId
    public Long getId() {
        return id;
    }

    // Setter for roomId
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for capacity
    public int getCapacity() {
        return capacity;
    }

    // Setter for capacity
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Getter for availability
    public boolean getAvailability() {
        return availability;
    }

    // Setter for availability
    public void setAvailabilty(boolean availability) {
        this.availability = availability;
    }
}
