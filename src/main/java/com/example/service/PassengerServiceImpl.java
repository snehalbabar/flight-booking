package com.example.service;

import com.example.exception.PassengerNotFoundException;
import com.example.model.Passenger;
import com.example.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(()->
                        new PassengerNotFoundException("No Passenger with id"+id+ " present"));
    }

    @Override
    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public Passenger updatePassenger(Long id, Passenger passenger) {
        if (passengerRepository.existsById(id)) {
            passenger.setId(id);
            return passengerRepository.save(passenger);
        }
        return null;
    }
}
