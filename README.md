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
* **URL**: `/api/auth/signup`
* **Method**: POST
* **Headers**: None
* **Request Body**:
```json
{
    "username": "string",
    "email": "string",
    "password": "string",
    "role": "ROLE_ADMIN"
}
```
* **Response**: User registration confirmation

### Sign In
* **URL**: `/api/auth/signin`
* **Method**: POST
* **Headers**: None
* **Request Body**:
```json
{
    "username": "string",
    "password": "string"
}
```
* **Response**: JWT token for authentication

### Sign Out
* **URL**: `/api/auth/signout`
* **Method**: POST
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: Logout confirmation

### Get All Users
* **URL**: `/api/users/`
* **Method**: GET
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: List of user objects

## Room Management

### Get All Rooms
* **URL**: `/api/rooms/`
* **Method**: GET
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: List of room objects

### Create Room
* **URL**: `/api/rooms/`
* **Method**: POST
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Request Body**:
```json
{
    "name": "string",
    "capacity": "integer",
    "availability": "boolean"
}
```
* **Response**: Created room object

### Get Room by ID
* **URL**: `/api/rooms/{id}`
* **Method**: GET
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: Room object

### Update Room
* **URL**: `/api/rooms/{id}`
* **Method**: PUT
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Request Body**:
```json
{
    "name": "string",
    "capacity": "integer",
    "availability": "boolean"
}
```
* **Response**: Updated room object

### Delete Room
* **URL**: `/api/rooms/{id}`
* **Method**: DELETE
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: Deletion confirmation

### Check Room Availability
* **URL**: `/api/rooms/available`
* **Method**: POST
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Request Body**:
```json
{
    "startTime": "yyyy-MM-ddTHH:mm:ss",
    "endTime": "yyyy-MM-ddTHH:mm:ss"
}
```
* **Response**: List of available room objects

## Booking Management

### Create Booking
* **URL**: `/api/bookings/`
* **Method**: POST
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Request Body**:
```json
{
    "roomId": "integer",
    "capacity": "integer",
    "startTime": "yyyy-MM-ddTHH:mm:ss",
    "endTime": "yyyy-MM-ddTHH:mm:ss"
}
```
* **Response**: Created booking object

### Get Booking by ID
* **URL**: `/api/bookings/{id}`
* **Method**: GET
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: Booking object

### Update Booking
* **URL**: `/api/bookings/{id}`
* **Method**: PUT
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Request Body**:
```json
{
    "room": {
        "id": "integer"
    },
    "capacity": "integer",
    "startTime": "yyyy-MM-ddTHH:mm:ss",
    "endTime": "yyyy-MM-ddTHH:mm:ss"
}
```
* **Response**: Updated booking object

### Cancel Booking
* **URL**: `/api/bookings/{id}`
* **Method**: DELETE
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: Cancellation confirmation

### Get User Bookings
* **URL**: `/api/bookings/user/{userId}/bookings`
* **Method**: GET
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: List of booking objects for the specified user

### Get All Bookings
* **URL**: `/api/bookings/`
* **Method**: GET
* **Headers**:
  * Authorization: Bearer {JWT_TOKEN}
* **Response**: List of all booking objects

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

