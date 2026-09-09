package com.llegaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.llegaya.model.Trip;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByActiveTrue();
}