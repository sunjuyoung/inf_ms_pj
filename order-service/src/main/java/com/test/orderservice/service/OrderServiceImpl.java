package com.test.orderservice.service;

import com.test.orderservice.dto.OrderDto;
import com.test.orderservice.entity.OrderEntity;
import com.test.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty()*orderDto.getUnitPrice());
        OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        OrderEntity save = orderRepository.save(orderEntity);
        OrderDto dto = modelMapper.map(save, OrderDto.class);

        return dto;
    }

    @Override
    public List<OrderDto> getOrdersByUserId(String userId) {
        List<OrderEntity> entityList = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtoList = new ArrayList<>();
        entityList.forEach(i->{
            orderDtoList.add(modelMapper.map(i,OrderDto.class));
        });
        return orderDtoList;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = modelMapper.map(orderEntity, OrderDto.class);
        return orderDto;
    }
}
