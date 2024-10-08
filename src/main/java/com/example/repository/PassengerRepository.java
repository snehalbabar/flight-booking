package com.example.repository;

import com.example.model.Booking;
import com.example.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  PassengerRepository extends JpaRepository<Passenger, Long> {

}