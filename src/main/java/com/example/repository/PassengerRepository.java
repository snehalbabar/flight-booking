package com.example.repository;

import com.example.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  PassengerRepository extends JpaRepository<Passenger, Long> {
}