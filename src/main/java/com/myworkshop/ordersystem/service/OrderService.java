package com.myworkshop.ordersystem.service;

import com.myworkshop.ordersystem.dto.OrderCreateRequest;
import com.myworkshop.ordersystem.dto.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderCreateRequest request);
}
