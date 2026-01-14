package com.keyin.sdat.sprintapi.controller;

import com.keyin.sdat.sprintapi.model.Airport;
import com.keyin.sdat.sprintapi.model.City;
import com.keyin.sdat.sprintapi.repository.AirportRepository;
import com.keyin.sdat.sprintapi.repository.CityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
@CrossOrigin
public class AirportController {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public AirportController(AirportRepository airportRepository, CityRepository cityRepository) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return airportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create airport and attach to a city
    @PostMapping
    public ResponseEntity<Airport> createAirport(
            @RequestParam Long cityId,
            @RequestBody Airport airport
    ) {
        City city = cityRepository.findById(cityId).orElse(null);
        if (city == null) return ResponseEntity.notFound().build();

        airport.setId(null);
        airport.setCity(city);

        Airport saved = airportRepository.save(airport);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport updated) {
        return airportRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setCode(updated.getCode());
                    return ResponseEntity.ok(airportRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        if (!airportRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        airportRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
