package com.example.exception;

public class BookingTooCloseToDepartureException extends RuntimeException{
    public BookingTooCloseToDepartureException(String message) {
        super(message);
    }

    public BookingTooCloseToDepartureException(String message, Throwable cause) {
        super(message, cause);
    }
}
