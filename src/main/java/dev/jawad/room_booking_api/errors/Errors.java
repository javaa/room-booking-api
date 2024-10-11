package dev.jawad.room_booking_api.errors;

import dev.jawad.room_booking_api.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum Errors implements ErrorResponse {
    NO_PERMISSION("NO_PERMISSION", HttpStatus.FORBIDDEN, "You do not have permission. Only Admin is allowed to perform this action."),
    ROOM_NOT_FOUND("ROOM_NOT_FOUND", HttpStatus.NOT_FOUND, "Room with id {id} not found"),
    INVALID_ROOM_CAPACITY("INVALID_ROOM_CAPACITY", HttpStatus.BAD_REQUEST, "The requested room capacity exceeds the maximum allowed capacity"),
    BOOKING_TIME_CONFLICT("BOOKING_TIME_CONFLICT", HttpStatus.CONFLICT, "The room is already booked for the selected time range"),
    USER_NOT_AUTHORIZED("USER_NOT_AUTHORIZED", HttpStatus.UNAUTHORIZED, "User is not authorized to perform this action"),
    START_TIME_AFTER_END_TIME("START_TIME_AFTER_END_TIME", HttpStatus.BAD_REQUEST, "Start time must be before end time."),
    PAST_BOOKING_TIME("PAST_BOOKING_TIME", HttpStatus.BAD_REQUEST, "Cannot book a room for past times."),
    ROOM_NOT_AVAILABLE("ROOM_NOT_AVAILABLE", HttpStatus.CONFLICT, "Room is not available during the requested time."),
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User not found"),
    JWT_TOKEN_EXPIRED_OR_INVALID("JWT_TOKEN_EXPIRED_OR_INVALID", HttpStatus.UNAUTHORIZED, "JWT token is expired or invalid");

    String key;
    HttpStatus httpStatus;
    String message;

    Errors(String key, HttpStatus httpStatus, String message) {
        this.message = message;
        this.key = key;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
