package com.myworkshop.ordersystem.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String errorCode,
        String message,
        String path
) {
}
