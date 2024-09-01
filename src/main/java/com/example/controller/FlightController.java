package com.example.controller;

import com.example.model.Flight;
import com.example.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @GetMapping("/all-flights")
    public ResponseEntity<List<Flight>> getAllFlightDetails() {
        List<Flight> flights = flightService.getAllFlight();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }



    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {

        try {
            //check if flight number already exists
            Flight existingFlight = flightService.getFlightByFlightNumber(flight.getFlightNumber());
            if (existingFlight != null) {
                logger.error("Flight creation failed: Flight number {} already exists", flight.getFlightNumber());
                return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
            }
            //if flight number doesn't exisit create new flight
            Flight createdFlight = flightService.saveFlight(flight);
            return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating flight: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFilghtdetailsById(@PathVariable Long id)
    {
        Flight flight = flightService.getFlightById(id);
        if(flight != null)
        {
            return new ResponseEntity<>(flight,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightById(@PathVariable Long id)
    {
         if(flightService.getFlightById(id) != null)
         {
             flightService.deletedFlightById(id);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }
         else {
             logger.error("Flight does not exist");
             return new ResponseEntity<>(HttpStatus.CONFLICT);
         }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlightDetails(@PathVariable Long id, @RequestBody Flight flight)
    {
        Flight upadatedFlight = flightService.updateFlight(id,flight);
        if(upadatedFlight != null)
        {
            return  new ResponseEntity<>(upadatedFlight, HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
