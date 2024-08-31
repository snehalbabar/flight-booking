package com.example.controller;

import com.example.model.Passenger;
import com.example.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping("/list")
    public List<Passenger> getAllPassenger() {
        return passengerRepository.findAll();
    }

    @PostMapping("/passengers")
    public Passenger createPerson(@RequestBody Passenger passenger ) {
        return passengerRepository.save(passenger);
    }
}
