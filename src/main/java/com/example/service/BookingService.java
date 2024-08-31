package com.example.service;

import com.example.model.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Booking saveBooking(Booking booking);
    void deleteBooking(Long id);
    List<Booking> getBookingsByFlightId(Long flightId);
    List<Booking> getBookingsByPassengerId(Long passengerId);
    Booking updateBooking(Long id, Booking booking);

}
