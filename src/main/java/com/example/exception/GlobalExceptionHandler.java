package com.example.exception;

import com.example.model.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        logger.error("Validation error: {}", ex.getMessage(), ex);

        // Collect field errors
        StringBuilder errorDetails = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorDetails.append(String.format("Field '%s': %s; ", fieldName, message));
        });

        ErrorResponse errorResponse = new ErrorResponse(
                "Validation error",
                HttpStatus.BAD_REQUEST.value(),
                errorDetails.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Constraint violation error: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Constraint violation error",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFlightNotFoundException(FlightNotFoundException ex) {
        logger.error("Flight not found: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Flight not found",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePassengerNotFoundException(PassengerNotFoundException ex) {
        logger.error("Passanger not found: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Passenger not found",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSeatAvailableException.class)
    public ResponseEntity<ErrorResponse> handleNoSeatAvailableException(NoSeatAvailableException ex) {
        logger.error("No seats are available {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "No seats are available",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingTooCloseToDepartureException.class)
    public ResponseEntity<ErrorResponse> handleBookingTooCloseToDepartureException(BookingTooCloseToDepartureException ex) {
        logger.error("Booking Too Close To Departure  {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Booking Too Close To Departure",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
