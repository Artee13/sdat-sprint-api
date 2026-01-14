package com.keyin.sdat.sprintapi.repository;

import com.keyin.sdat.sprintapi.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {}
