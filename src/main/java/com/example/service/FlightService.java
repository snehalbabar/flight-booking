package com.example.service;

import com.example.model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlight();

    Flight getFlightById(Long id);

    Flight saveFlight(Flight flight);

    void deletedFlightById(Long id);

    Flight updateFlight(Long id, Flight flight);

    //find flight by flight number
    Flight getFlightByFlightNumber(String flightNumber);
}
