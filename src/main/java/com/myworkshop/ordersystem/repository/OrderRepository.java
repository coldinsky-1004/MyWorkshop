package com.myworkshop.ordersystem.repository;

import com.myworkshop.ordersystem.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
