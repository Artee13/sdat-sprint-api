package com.keyin.sdat.sprintapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String airlineName;
    private Integer numberOfPassengers;

    @ManyToMany(mappedBy = "aircraftFlown")
    @JsonIgnore
    @Builder.Default
    private Set<Passenger> passengers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "aircraft_takeoff_airports",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    @Builder.Default
    private Set<Airport> takeoffAirports = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "aircraft_landing_airports",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    @Builder.Default
    private Set<Airport> landingAirports = new HashSet<>();
}
