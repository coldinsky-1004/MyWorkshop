package com.myworkshop.ordersystem.repository;

import com.myworkshop.ordersystem.domain.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s join fetch s.product p where p.id = :productId")
    Optional<Stock> findByProductIdForUpdate(Long productId);
}
