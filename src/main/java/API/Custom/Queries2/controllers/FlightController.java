package API.Custom.Queries2.controllers;

import API.Custom.Queries2.entities.Flight;
import API.Custom.Queries2.entities.FlightStatus;
import API.Custom.Queries2.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    public String randomValueForFlight() {
        Random random = new Random();
        int limitA = 20;
        int limitZ = 150;
        int targetStringLength = 11;

        return random.ints(limitA, limitZ + 1)
                .limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @GetMapping("/provisioning")
    public void provFlight(@RequestParam(required = false) Integer n) {
        if (n == null) n = 100;
        List<Flight> newFlight = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Flight flight = new Flight();
            flight.setDescription(randomValueForFlight());
            flight.setFromAirport(randomValueForFlight());
            flight.setFlightStatus(FlightStatus.randomStatus());
            flight.setToAirport(randomValueForFlight());
            newFlight.add(flight);
        }
        flightRepository.saveAll(newFlight);
    }


    @GetMapping("/customQuery")
    public List<Flight> getAllCustomFlight(@RequestParam FlightStatus p1, @RequestParam FlightStatus p2) {
        return flightRepository.getCustomFlight(p1, p2);
    }

    @GetMapping("/page")
    public Page<Flight> getAllFlightInPages(@RequestParam int page, @RequestParam int size) {
        return flightRepository.findAll(PageRequest.of(page, size, Sort.by("fromAirport").ascending()));
    }

    @GetMapping("/status")
    public List<Flight> getAllFlightByStatus() {
        return flightRepository.findByFlightStatus(FlightStatus.ON_TIME);
    }
}