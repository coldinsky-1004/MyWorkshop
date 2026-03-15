package com.myworkshop.ordersystem.exception;

public class OutOfStockException extends BusinessException {

    public OutOfStockException(String errorCode, String message) {
        super(errorCode, message);
    }
}
