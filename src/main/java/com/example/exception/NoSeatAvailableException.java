package com.example.exception;

public class NoSeatAvailableException extends RuntimeException{
    public NoSeatAvailableException(String message) {
        super(message);
    }

    public NoSeatAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
