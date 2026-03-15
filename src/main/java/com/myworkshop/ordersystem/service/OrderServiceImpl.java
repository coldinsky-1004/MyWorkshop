package com.myworkshop.ordersystem.service;

import com.myworkshop.ordersystem.domain.Order;
import com.myworkshop.ordersystem.domain.Product;
import com.myworkshop.ordersystem.domain.Stock;
import com.myworkshop.ordersystem.dto.OrderCreateRequest;
import com.myworkshop.ordersystem.dto.OrderResponse;
import com.myworkshop.ordersystem.exception.NotFoundException;
import com.myworkshop.ordersystem.exception.OutOfStockException;
import com.myworkshop.ordersystem.repository.OrderRepository;
import com.myworkshop.ordersystem.repository.ProductRepository;
import com.myworkshop.ordersystem.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderCreateRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND", "상품을 찾을 수 없습니다."));

        Stock stock = stockRepository.findByProductIdForUpdate(request.productId())
                .orElseThrow(() -> new NotFoundException("STOCK_NOT_FOUND", "재고 정보를 찾을 수 없습니다."));

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
