package com.example.controller;

import com.example.model.Booking;
import com.example.service.BookingService;
import com.example.service.FlightService;
import com.example.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingService bookingService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private PassengerService passengerService;

    @GetMapping("/list")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            logger.error("Booking with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.saveBooking(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating booking with flightId {} and passengerId {}: {}", booking.getFlightId(), booking.getPassengerId(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error creating booking with flightId {} and passengerId {}: {}", booking.getFlightId(), booking.getPassengerId(), e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, booking);
            if (updatedBooking != null) {
                return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
            } else {
                logger.error("Booking with ID {} not found for update", id);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error updating booking: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error updating booking: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> CancelBooking(@PathVariable Long id) {
        try {
            if (bookingService.getBookingById(id) != null) {
                bookingService.deleteBooking(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.error("Booking with ID {} not found for deletion", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Unexpected error deleting booking: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Booking>> getBookingsByFlightId(@PathVariable Long flightId) {
        List<Booking> bookings = bookingService.getBookingsByFlightId(flightId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<Booking>> getBookingsByPassengerId(@PathVariable Long passengerId) {
        List<Booking> bookings = bookingService.getBookingsByPassengerId(passengerId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);

    }



}
