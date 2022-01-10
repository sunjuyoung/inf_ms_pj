package com.test.orderservice.controller;

import com.test.orderservice.dto.KafkaOrderDto;
import com.test.orderservice.dto.OrderDto;
import com.test.orderservice.dto.RequestOrder;
import com.test.orderservice.dto.ResponseOrder;
import com.test.orderservice.messagequeue.KafkaProducer;
import com.test.orderservice.messagequeue.OrderProducer;
import com.test.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;
    private final Environment env;
    private final ModelMapper modelMapper;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;


    @GetMapping("/health_check")
    public String status(){
        return String.format("working...port : %s",env.getProperty("local.server.port"));
    }


    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder requestOrder){
        OrderDto orderDto = modelMapper.map(requestOrder,OrderDto.class);
        orderDto.setUserId(userId);
      /*  OrderDto saveOrderDto = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = modelMapper.map(saveOrderDto, ResponseOrder.class);*/

        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(requestOrder.getQty()*requestOrder.getUnitPrice());

        /*send order to kafka*/
        kafkaProducer.send("example-catalog-topic",orderDto);
        orderProducer.send("orders",orderDto);

        ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);

        return ResponseEntity.ok().body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrdersByOrderId(@PathVariable("userId") String userId){
        List<OrderDto> orderDtoList = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> responseOrderList = new ArrayList<>();

        orderDtoList.forEach(i->{
            responseOrderList.add(modelMapper.map(i,ResponseOrder.class));
        });
        return ResponseEntity.ok().body(responseOrderList);
    }
}
