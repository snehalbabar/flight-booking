package com.example.controller;

import com.example.model.Flight;
import com.example.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/all-flights")
    public ResponseEntity<List<Flight>> getAllFlightDetails(){
        List<Flight> flights = flightService.getAllFlight();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }


    @PostMapping("/flights")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight)
    {

        try{
            //check if flight number already exists
            Flight existingFlight = flightService.getFlightByFlightNumber(flight.getFlightNumber());
            if (existingFlight != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
            }
            //if flight number doesn't exisit create new flight
            Flight createdFlight = flightService.saveFlight(flight);
            return new ResponseEntity<>(createdFlight,HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
