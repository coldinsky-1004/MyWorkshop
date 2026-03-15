package com.myworkshop.ordersystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long orderId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal totalPrice,
        LocalDateTime orderedAt,
        String status
) {
}
