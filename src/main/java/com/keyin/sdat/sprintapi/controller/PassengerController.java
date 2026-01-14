package com.keyin.sdat.sprintapi.controller;

import com.keyin.sdat.sprintapi.model.Passenger;
import com.keyin.sdat.sprintapi.model.City;
import com.keyin.sdat.sprintapi.model.Aircraft;
import com.keyin.sdat.sprintapi.repository.PassengerRepository;
import com.keyin.sdat.sprintapi.repository.CityRepository;
import com.keyin.sdat.sprintapi.repository.AircraftRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
@CrossOrigin
public class PassengerController {

    private final PassengerRepository passengerRepository;
    private final CityRepository cityRepository;
    private final AircraftRepository aircraftRepository;

    public PassengerController(
            PassengerRepository passengerRepository,
            CityRepository cityRepository,
            AircraftRepository aircraftRepository) {
        this.passengerRepository = passengerRepository;
        this.cityRepository = cityRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
        return passengerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create passenger and attach to city
    @PostMapping
    public ResponseEntity<Passenger> createPassenger(
            @RequestParam Long cityId,
            @RequestBody Passenger passenger) {
        City city = cityRepository.findById(cityId).orElse(null);
        if (city == null)
            return ResponseEntity.notFound().build();

        passenger.setId(null);
        passenger.setCity(city);

        return ResponseEntity.ok(passengerRepository.save(passenger));
    }

    // Assign aircraft to passenger
    @PutMapping("/{passengerId}/aircraft/{aircraftId}")
    public ResponseEntity<Passenger> assignAircraft(
            @PathVariable Long passengerId,
            @PathVariable Long aircraftId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElse(null);
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElse(null);

        if (passenger == null || aircraft == null) {
            return ResponseEntity.notFound().build();
        }

        passenger.getAircraftFlown().add(aircraft);
        return ResponseEntity.ok(passengerRepository.save(passenger));
    }
}
