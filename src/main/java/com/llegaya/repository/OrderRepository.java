package com.llegaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.llegaya.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);

    List<Order> findByDriverId(Long driverId);
}