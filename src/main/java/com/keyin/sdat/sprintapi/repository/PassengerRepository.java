package com.keyin.sdat.sprintapi.repository;

import com.keyin.sdat.sprintapi.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {}
