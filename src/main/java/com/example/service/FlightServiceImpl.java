package com.example.service;

import com.example.exception.FlightNotFoundException;
import com.example.model.Flight;
import com.example.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public List<Flight> getAllFlight() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(Long id)
    {
        return flightRepository.findById(id)
                .orElseThrow(()->
                        new FlightNotFoundException("flight with id" + id + " not found"));
    }

    @Override
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public void deletedFlightById(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight updateFlight(Long id, Flight flight) {
        if(flightRepository.existsById(id))
        {
            flight.setId(id);
            return flightRepository.save(flight);
        }
        return null;
    }

    @Override
    public Flight getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber).orElse(null);
    }
}
