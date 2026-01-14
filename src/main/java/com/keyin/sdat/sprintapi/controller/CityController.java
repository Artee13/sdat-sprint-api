package com.keyin.sdat.sprintapi.controller;

import com.keyin.sdat.sprintapi.model.City;
import com.keyin.sdat.sprintapi.repository.CityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@CrossOrigin
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // GET all cities
    @GetMapping
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // GET city by id
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return cityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create city
    @PostMapping
    public City createCity(@RequestBody City city) {
        city.setId(null);
        return cityRepository.save(city);
    }

    // PUT update city
    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(
            @PathVariable Long id,
            @RequestBody City updatedCity
    ) {
        return cityRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCity.getName());
                    existing.setState(updatedCity.getState());
                    existing.setPopulation(updatedCity.getPopulation());
                    return ResponseEntity.ok(cityRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE city
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        if (!cityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cityRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
