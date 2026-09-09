package com.llegaya.controller;

import com.llegaya.model.Order;
import com.llegaya.model.Trip;
import com.llegaya.repository.OrderRepository;
import com.llegaya.service.MatchingService;
import com.llegaya.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    private final MatchingService matchingService;

    public OrderController(
            OrderService orderService,
            OrderRepository orderRepository,
            MatchingService matchingService) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.matchingService = matchingService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderService.createOrder(order);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("/{orderId}/verify-pin")
    public ResponseEntity<String> verifyPinAndReleasePayment(
            @PathVariable Long orderId,
            @RequestParam String pin) {

        try {
            boolean success = orderService.confirmDeliveryAndReleasePayment(orderId, pin);
            if (success) {
                return ResponseEntity.ok("Entrega confirmada y pago liberado con éxito.");
            }
            return ResponseEntity.badRequest().body("No se pudo completar la entrega.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}/matching-trips")
    public ResponseEntity<List<Trip>> getMatchingTrips(@PathVariable Long orderId) {
        Order order = orderRepository.findById(Objects.requireNonNull(orderId, "orderId"))
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        List<Trip> matchingTrips = matchingService.findMatchingTrips(order);
        return ResponseEntity.ok(matchingTrips);
    }
}