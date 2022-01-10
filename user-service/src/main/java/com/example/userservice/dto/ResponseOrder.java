package com.example.userservice.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResponseOrder implements Serializable {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private Date createdAt;

}
