package com.example.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private String email;
    private String pwd;
    private String encryptedPwd;
    private String name;
    private String userId;
    private Date createdAt;

    private List<ResponseOrder> orders = new ArrayList<>();


}
