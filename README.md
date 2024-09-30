# Room Booking API

## Overview

The Room Booking API is a RESTful service that allows users to manage room bookings. Users can check available rooms, create bookings, and validate room availability based on specified time ranges. This API is designed for ease of use and security, ensuring that only valid bookings are processed.

## Features

- **User Authentication**: Secure endpoints with JWT-based authentication.
- **Room Management**: Create, read, update, and delete room information.
- **Booking Management**: Create bookings, check availability, and validate booking times.
- **Availability Check**: Users can check which rooms are available for a given time range.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL (or any SQL database)
- Maven
- Lombok (for reducing boilerplate code)

## API Endpoints

### 1. User Authentication

- **POST /api/auth/signup**
  - Create a user.

- **POST /api/auth/signin**
  - Authenticate the user and return a JWT token.

### 2. Room Management

- **GET /api/rooms**
  - Retrieve all rooms.
  
- **POST /api/rooms**
  - Create a new room.
  
- **GET /api/rooms/{id}**
  - Get details of a specific room by ID.
  
- **PUT /api/rooms/{id}**
  - Update details of a specific room by ID.
  
- **DELETE /api/rooms/{id}**
  - Delete a specific room by ID.

### 3. Booking Management

- **POST /api/bookings/**
  - Create a new booking.
  
- **GET /api/bookings/{id}**
  - Retrieve details of a specific booking by ID.

### 4. Room Availability Check

- **POST /api/rooms/available**
  - Check available rooms within a given time range.
  
#### Request Body Example:
```json
{
    "startTime": "2024-10-01T10:00:00",
    "endTime": "2024-10-01T12:00:00"
}