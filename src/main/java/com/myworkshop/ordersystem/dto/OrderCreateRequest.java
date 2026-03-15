package com.myworkshop.ordersystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity
) {
}
