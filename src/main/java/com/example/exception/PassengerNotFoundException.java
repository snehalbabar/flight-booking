package com.example.exception;

public class PassengerNotFoundException extends RuntimeException{
    public PassengerNotFoundException(String message) {
        super(message);
    }

    public PassengerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
