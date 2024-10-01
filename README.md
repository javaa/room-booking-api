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
- H2 Database
- Maven

# RoomBookingAPI Documentation

This document provides an overview of the RoomBookingAPI endpoints, including authentication, room management, and booking functionalities.

## Base URL

All endpoints are relative to: `http://localhost:8081/api`

## Authentication

### Sign Up

Create a new user account.

- **URL**: `/auth/signup`
- **Method**: POST
- **Body**:
  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string",
    "role": "string"
  }
  ```
- **Response**: User creation confirmation

### Sign In

Authenticate a user and receive a JWT token.

- **URL**: `/auth/signin`
- **Method**: POST
- **Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response**: JWT token for authentication

### Sign Out

Invalidate the current user's session.

- **URL**: `/auth/signout`
- **Method**: POST
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Response**: Signout confirmation

### Get All Users

Retrieve a list of all users (admin only).

- **URL**: `/auth/users`
- **Method**: GET
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Response**: List of user objects

## Room Management

### Get All Rooms

Retrieve a list of all available rooms.

- **URL**: `/rooms/`
- **Method**: GET
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Response**: List of room objects

### Create Room

Add a new room to the system (admin only).

- **URL**: `/rooms/`
- **Method**: POST
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Body**:
  ```json
  {
    "name": "string",
    "capacity": "integer",
    "availability": "boolean"
  }
  ```
- **Response**: Created room object

### Check Room Availability

Check room availability for a specific time slot.

- **URL**: `/rooms/available`
- **Method**: POST
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Body**:
  ```json
  {
    "startTime": "ISO8601 datetime",
    "endTime": "ISO8601 datetime"
  }
  ```
- **Response**: List of available room objects

## Booking Management

### Create Booking

Create a new room booking.

- **URL**: `/bookings/`
- **Method**: POST
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Body**:
  ```json
  {
    "roomId": "integer",
    "capacity": "integer",
    "startTime": "ISO8601 datetime",
    "endTime": "ISO8601 datetime"
  }
  ```
- **Response**: Created booking object

### List User Bookings

Retrieve all bookings for a specific user.

- **URL**: `/bookings/user/{userId}/bookings`
- **Method**: GET
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Response**: List of booking objects for the specified user

### Cancel Booking

Cancel an existing booking by its ID.

- **URL**: `/bookings/{bookingId}`
- **Method**: DELETE
- **Headers**:
  - Authorization: Bearer {JWT_TOKEN}
- **Response**: Booking cancellation confirmation

## Error Handling

All endpoints may return appropriate HTTP status codes for different scenarios:

- 200 OK: Successful operation
- 201 Created: Resource successfully created
- 400 Bad Request: Invalid input or parameters
- 401 Unauthorized: Authentication failure or invalid token
- 403 Forbidden: Insufficient permissions
- 404 Not Found: Requested resource not found
- 500 Internal Server Error: Unexpected server error

For detailed error messages, refer to the response body of the API calls.