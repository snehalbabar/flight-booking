package com.example.Service;

import com.example.model.Flight;
import com.example.repository.FlightRepository;
import com.example.service.FlightService;
import com.example.service.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService ;

    private Flight flight;

    @BeforeEach
    void setup()
    {
        MockitoAnnotations.openMocks(this);
        LocalDateTime dateTime = LocalDateTime.of(2024, 8, 31, 10, 30);
        flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AI202");
        flight.setDepartureCity("New York");
        flight.setArrivalCity("Los Angeles");
        flight.setDepartureTime(dateTime);
        flight.setSeatsAvailable(150);
        flight.setPricePerSeat(new BigDecimal("123.456"));

    }

    @Test
    void testGetFlightById_FlightFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Flight foundFlight = flightService.getFlightById(1L);

        assertNotNull(foundFlight);
        assertEquals("AI202", foundFlight.getFlightNumber());
    }
}
