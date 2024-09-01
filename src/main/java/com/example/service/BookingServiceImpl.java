package com.example.service;

import com.example.exception.*;
import com.example.model.Booking;
import com.example.model.Flight;
import com.example.model.Passenger;
import com.example.repository.BookingRepository;
import com.example.repository.FlightRepository;
import com.example.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return bookingRepository.findById(id)
                .orElseThrow(()->
                        new BookingNotFoundException("No booking with id" + id + " found"));
    }

    @Transactional
    @Override
    public Booking saveBooking(Booking booking) {
        // Validate flight and passenger
        Optional<Flight> flight = flightRepository.findById(booking.getFlightId());
        Optional<Passenger> passenger = passengerRepository.findById(booking.getPassengerId());

        if (!flight.isPresent()) {
            throw new FlightNotFoundException("No flight with flight id " + booking.getFlightId() + " found");
        }
        if (!passenger.isPresent()) {
            throw new PassengerNotFoundException("No passenger with flight id " + booking.getPassengerId() + " found");
        }

        Flight flightData = flight.get();

        //Decrement available seats
        if(flightData.getSeatsAvailable() <= 0)
        {
            throw  new NoSeatAvailableException("Flight is already booked!! No seats available.");
        }

        flightData.setSeatsAvailable(flightData.getSeatsAvailable()-1);
            //set same id to record and save
        flightData.setId(flightData.getId());
        flightRepository.save(flightData);


        //chcek if booking is made before atleast 2hours
        if(isDepaturetimeLess(flightData.getDepartureTime()))
        {
            throw new BookingTooCloseToDepartureException("Cannot book the flight within 2 hours of departure.");
        }

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        // Validate flight and passenger
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        Booking  bookingDetails = bookingOptional.get();
        Optional<Flight> flightOptional = flightRepository.findById(bookingDetails.getFlightId());
        Flight flightDetails = flightOptional.get();

        bookingRepository.deleteById(id);

        //incremenet available seats
        flightDetails.setSeatsAvailable(flightDetails.getSeatsAvailable()+1);
        //set same id to record and save
        flightDetails.setId(flightDetails.getId());
        flightRepository.save(flightDetails);
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


    private Boolean isDepaturetimeLess(LocalDateTime deaptureTime)
    {
        // Check if the booking is made within 2 hours of departure
        LocalDateTime now = LocalDateTime.now();
        if (deaptureTime.minusHours(2).isBefore(now)) {
           return false;
        }
        return true;
    }

}
