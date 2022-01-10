package com.test.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.orderservice.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    List<Field> fieldList = Arrays.asList(
            new Field("string",true,"order_id"),
            new Field("string",true,"user_id"),
            new Field("string",true,"product_id"),
            new Field("int32",true,"unit_price"),
            new Field("int32",true,"total_price"),
            new Field("int32",true,"qty")
            );

    Schema schema = Schema.builder()
                    .fields(fieldList)
                    .type("struct")
                    .name("orders")
                    .optional(false)
                    .build();



    public OrderDto send(String topic,OrderDto orderDto){
        Payload payload = Payload.builder()
                            .order_id(orderDto.getOrderId())
                            .product_id(orderDto.getProductId())
                            .user_id(orderDto.getUserId())
                            .qty(orderDto.getQty())
                            .unit_price(orderDto.getUnitPrice())
                            .total_price(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema,payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(kafkaOrderDto);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        kafkaTemplate.send(topic,jsonString);
        log.info("order producer sent data from Order microservice : {}",kafkaOrderDto);

        return orderDto;
    }
}
