package com.example.userservice.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {

    @Email
    @NotNull(message = "email cannot not be null")
    @Size(min = 2, message = "email not be less than two characters")
    private String email;

    @NotNull(message = "password cannot not be null")
    @Size(min = 4, message = "password not be less than four characters")
    private String password;


}
