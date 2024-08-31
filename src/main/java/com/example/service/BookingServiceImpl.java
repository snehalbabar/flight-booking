package com.example.service;

import com.example.model.Booking;
import com.example.model.Flight;
import com.example.model.Passenger;
import com.example.repository.BookingRepository;
import com.example.repository.FlightRepository;
import com.example.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public Booking saveBooking(Booking booking) {
        // Validate flight and passenger
        Optional<Flight> flight = flightRepository.findById(booking.getFlightId());
        Optional<Passenger> passenger = passengerRepository.findById(booking.getPassengerId());

        if (!flight.isPresent()) {
            throw new IllegalArgumentException("Flight not found");
        }
        if (!passenger.isPresent()) {
            throw new IllegalArgumentException("Passenger not found");
        }

        // Set the entities in the booking
        //booking.setFlight(flight.get());
       //booking.setPassenger(passenger.get());

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getBookingsByFlightId(Long flightId) {
        return bookingRepository.findByFlightId(flightId);
    }

    @Override
    public List<Booking> getBookingsByPassengerId(Long passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        if (bookingRepository.existsById(id)) {
            // Validate flight and passenger
            Optional<Flight> flight = flightRepository.findById(booking.getFlightId());
            Optional<Passenger> passenger = passengerRepository.findById(booking.getPassengerId());

            if (!flight.isPresent()) {
                throw new IllegalArgumentException("Flight not found");
            }
            if (!passenger.isPresent()) {
                throw new IllegalArgumentException("Passenger not found");
            }

            booking.setId(id);
            return bookingRepository.save(booking);
        }
        return null;
    }
}
