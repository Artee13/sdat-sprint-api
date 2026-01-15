package com.keyin.sdat.sprintapi.controller;

import com.keyin.sdat.sprintapi.model.Aircraft;
import com.keyin.sdat.sprintapi.model.Airport;
import com.keyin.sdat.sprintapi.model.City;
import com.keyin.sdat.sprintapi.model.Passenger;
import com.keyin.sdat.sprintapi.repository.AircraftRepository;
import com.keyin.sdat.sprintapi.repository.CityRepository;
import com.keyin.sdat.sprintapi.repository.PassengerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reports")
@CrossOrigin
public class ReportsController {

    private final CityRepository cityRepository;
    private final PassengerRepository passengerRepository;
    private final AircraftRepository aircraftRepository;

    public ReportsController(CityRepository cityRepository,
                             PassengerRepository passengerRepository,
                             AircraftRepository aircraftRepository) {
        this.cityRepository = cityRepository;
        this.passengerRepository = passengerRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @GetMapping("/airports-by-city")
    public List<Map<String, Object>> airportsByCity() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (City city : cityRepository.findAll()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("cityId", city.getId());
            row.put("cityName", city.getName());
            row.put("state", city.getState());
            row.put("airports", city.getAirports());
            result.add(row);
        }
        return result;
    }

    @GetMapping("/aircraft-by-passenger")
    public List<Map<String, Object>> aircraftByPassenger() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Passenger p : passengerRepository.findAll()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("passengerId", p.getId());
            row.put("passengerName", p.getFirstName() + " " + p.getLastName());
            row.put("aircraft", p.getAircraftFlown());
            result.add(row);
        }
        return result;
    }

    @GetMapping("/airports-by-aircraft")
    public List<Map<String, Object>> airportsByAircraft() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Aircraft a : aircraftRepository.findAll()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("aircraftId", a.getId());
            row.put("aircraftLabel", a.getAirlineName() + " - " + a.getType());
            row.put("takeoffAirports", a.getTakeoffAirports());
            row.put("landingAirports", a.getLandingAirports());
            result.add(row);
        }
        return result;
    }

    @GetMapping("/airports-used-by-passenger")
    public List<Map<String, Object>> airportsUsedByPassenger() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Passenger p : passengerRepository.findAll()) {
            Set<Airport> used = new LinkedHashSet<>();

            for (Aircraft a : p.getAircraftFlown()) {
                used.addAll(a.getTakeoffAirports());
                used.addAll(a.getLandingAirports());
            }

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("passengerId", p.getId());
            row.put("passengerName", p.getFirstName() + " " + p.getLastName());
            row.put("airportsUsed", used);
            result.add(row);
        }

        return result;
    }
}
