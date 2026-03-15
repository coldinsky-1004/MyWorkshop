package com.myworkshop.ordersystem.service;

import com.myworkshop.ordersystem.domain.Order;
import com.myworkshop.ordersystem.domain.Product;
import com.myworkshop.ordersystem.domain.Stock;
import com.myworkshop.ordersystem.dto.OrderCreateRequest;
import com.myworkshop.ordersystem.dto.OrderResponse;
import com.myworkshop.ordersystem.exception.NotFoundException;
import com.myworkshop.ordersystem.exception.OutOfStockException;
import com.myworkshop.ordersystem.repository.OrderRepository;
import com.myworkshop.ordersystem.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderCreateRequest request) {
        Stock stock = stockRepository.findByProductIdForUpdate(request.productId())
                .orElseThrow(() -> new NotFoundException("STOCK_NOT_FOUND", "재고 정보를 찾을 수 없습니다."));

        Product product = stock.getProduct();

        if (stock.getQuantity() < request.quantity()) {
            throw new OutOfStockException("OUT_OF_STOCK", "재고가 부족합니다.");
        }

        stock.decrease(request.quantity());

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(request.quantity()));
        Order order = new Order(product, request.quantity(), totalPrice, LocalDateTime.now());
        Order saved = orderRepository.save(order);

        return new OrderResponse(
                saved.getId(),
                product.getId(),
                product.getName(),
                saved.getQuantity(),
                saved.getTotalPrice(),
                saved.getOrderedAt(),
                saved.getStatus().name()
        );
    }
}
