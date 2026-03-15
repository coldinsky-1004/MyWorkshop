package com.myworkshop.ordersystem.service;

import com.myworkshop.ordersystem.domain.Order;
import com.myworkshop.ordersystem.domain.Product;
import com.myworkshop.ordersystem.domain.Stock;
import com.myworkshop.ordersystem.dto.OrderCreateRequest;
import com.myworkshop.ordersystem.dto.OrderResponse;
import com.myworkshop.ordersystem.exception.OutOfStockException;
import com.myworkshop.ordersystem.repository.OrderRepository;
import com.myworkshop.ordersystem.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @SuppressWarnings("null")
    @Test
    @DisplayName("BVA - 재고가 요청 수량(1)과 같을 때 주문 성공 및 재고 차감")
    void placeOrder_success_whenStockIsOne() {
        Product product = new Product("Keyboard", new BigDecimal("10000"));
        ReflectionTestUtils.setField(product, "id", 10L);

        Stock stock = new Stock(product, 1);
        ReflectionTestUtils.setField(stock, "id", 20L);

        when(stockRepository.findByProductIdForUpdate(10L)).thenReturn(Optional.of(stock));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order saved = invocation.getArgument(0);
            ReflectionTestUtils.setField(saved, "id", 30L);
            ReflectionTestUtils.setField(saved, "orderedAt", LocalDateTime.of(2026, 3, 15, 10, 0));
            return saved;
        });

        OrderResponse response = orderService.placeOrder(new OrderCreateRequest(10L, 1));

        assertThat(stock.getQuantity()).isZero();
        assertThat(response.orderId()).isEqualTo(30L);
        assertThat(response.productId()).isEqualTo(10L);
        assertThat(response.quantity()).isEqualTo(1);
        assertThat(response.totalPrice()).isEqualByComparingTo("10000");
        assertThat(response.status()).isEqualTo("CREATED");

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getQuantity()).isEqualTo(1);
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("BVA - 재고가 하한(0)일 때 주문 실패 및 OutOfStockException 발생")
    void placeOrder_fail_whenStockIsZero() {
        Product product = new Product("Keyboard", new BigDecimal("10000"));
        ReflectionTestUtils.setField(product, "id", 10L);

        Stock stock = new Stock(product, 0);
        ReflectionTestUtils.setField(stock, "id", 20L);

        when(stockRepository.findByProductIdForUpdate(10L)).thenReturn(Optional.of(stock));

        assertThatThrownBy(() -> orderService.placeOrder(new OrderCreateRequest(10L, 1)))
                .isInstanceOf(OutOfStockException.class)
                .hasMessage("재고가 부족합니다.");

        assertThat(stock.getQuantity()).isZero();
        verify(orderRepository, never()).save(any(Order.class));
    }
}
