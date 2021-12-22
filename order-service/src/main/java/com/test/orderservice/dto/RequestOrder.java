package com.test.orderservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RequestOrder {

    private String productId;
    private Integer qty;
    private Integer unitPrice;

}
