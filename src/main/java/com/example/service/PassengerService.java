package com.example.service;

import com.example.model.Passenger;

import java.util.List;

public interface PassengerService {

    List<Passenger> getAllPassengers();

    Passenger getPassengerById(Long id);

    Passenger savePassenger(Passenger passenger);

    void deletePassenger(Long id);

    Passenger updatePassenger(Long id, Passenger passenger);
}
