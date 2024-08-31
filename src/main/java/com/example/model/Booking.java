package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingDate;

    private double totalAmount;


    @JoinColumn(name = "flight_id")
    private Long flightId;;

    @JoinColumn(name ="passenger_id")
    private Long passengerId;

    @Enumerated(EnumType.STRING)
    private FlightStatus  status;

}
