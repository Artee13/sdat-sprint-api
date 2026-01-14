package com.keyin.sdat.sprintapi.controller;

import com.keyin.sdat.sprintapi.model.Aircraft;
import com.keyin.sdat.sprintapi.model.Airport;
import com.keyin.sdat.sprintapi.repository.AircraftRepository;
import com.keyin.sdat.sprintapi.repository.AirportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircraft")
@CrossOrigin
public class AircraftController {

    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;

    public AircraftController(AircraftRepository aircraftRepository, AirportRepository airportRepository) {
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
    }

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        aircraft.setId(null);
        return aircraftRepository.save(aircraft);
    }

    @PutMapping("/{aircraftId}/takeoff-airport/{airportId}")
    public ResponseEntity<Aircraft> addTakeoffAirport(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElse(null);
        Airport airport = airportRepository.findById(airportId).orElse(null);

        if (aircraft == null || airport == null) return ResponseEntity.notFound().build();

        aircraft.getTakeoffAirports().add(airport);
        return ResponseEntity.ok(aircraftRepository.save(aircraft));
    }

    @PutMapping("/{aircraftId}/landing-airport/{airportId}")
    public ResponseEntity<Aircraft> addLandingAirport(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElse(null);
        Airport airport = airportRepository.findById(airportId).orElse(null);

        if (aircraft == null || airport == null) return ResponseEntity.notFound().build();

        aircraft.getLandingAirports().add(airport);
        return ResponseEntity.ok(aircraftRepository.save(aircraft));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        if (!aircraftRepository.existsById(id)) return ResponseEntity.notFound().build();
        aircraftRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
