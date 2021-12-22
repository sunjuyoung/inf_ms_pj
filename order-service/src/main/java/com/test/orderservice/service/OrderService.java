package com.test.orderservice.service;

import com.test.orderservice.dto.OrderDto;
import com.test.orderservice.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getOrdersByUserId(String userId);
    OrderDto getOrderByOrderId(String orderId);
}
