package com.llegaya.service;

import com.llegaya.model.Order;
import com.llegaya.model.OrderStatus;
import com.llegaya.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Objects;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String generateDeliveryPin() {
        SecureRandom random = new SecureRandom();
        int pin = 1000 + random.nextInt(9000);
        return String.valueOf(pin);
    }

    @Transactional
    public Order createOrder(Order order) {
        order.setDeliveryPin(generateDeliveryPin());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Transactional
    public boolean confirmDeliveryAndReleasePayment(Long orderId, String inputPin) {
        Order order = orderRepository.findById(Objects.requireNonNull(orderId, "orderId"))
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!Objects.equals(order.getDeliveryPin(), inputPin)) {
            throw new IllegalArgumentException("El PIN ingresado es incorrecto");
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setPaymentReleased(true);
        orderRepository.save(order);

        if (order.getDriver() != null && order.getShippingFee() != null) {
            processPayoutToDriver(order.getDriver().getId(), order.getShippingFee());
        }

        return true;
    }

    private void processPayoutToDriver(Long driverId, BigDecimal amount) {
        System.out.println("Pago de $" + amount + " liberado exitosamente al conductor ID: " + driverId);
    }
}