package com.llegaya.service;

import com.llegaya.model.Order;
import com.llegaya.model.Trip;
import com.llegaya.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    private final TripRepository tripRepository;

    public MatchingService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double MAX_DIVERGENT_DISTANCE_KM = 3.0;

    public List<Trip> findMatchingTrips(Order order) {
        List<Trip> activeTrips = tripRepository.findByActiveTrue();

        return activeTrips.stream()
                .filter(trip -> calculateDistance(trip.getOriginLat(), trip.getOriginLng(), order.getOriginLat(),
                        order.getOriginLng()) <= MAX_DIVERGENT_DISTANCE_KM)
                .filter(trip -> calculateDistance(trip.getDestLat(), trip.getDestLng(), order.getDestLat(),
                        order.getDestLng()) <= MAX_DIVERGENT_DISTANCE_KM)
                .collect(Collectors.toList());
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}