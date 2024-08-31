package com.example.repository;

import com.example.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookigRepository extends JpaRepository<Booking, Long> {
}
