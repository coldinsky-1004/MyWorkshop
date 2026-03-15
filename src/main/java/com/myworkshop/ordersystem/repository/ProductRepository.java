package com.myworkshop.ordersystem.repository;

import com.myworkshop.ordersystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
