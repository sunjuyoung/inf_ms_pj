package com.example.userservice.controller;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UsersController {

    private final Environment env;
    private final UserService userService;
    private final ModelMapper modelMapper;

/*    @Value("${greeting.message}")
    private String welcomeMessage;*/

    @GetMapping("/health_check")
    public String status(){
        return "working...";
    }
    @GetMapping("/welcome")
    public String welcome(){
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user){
        UserDto userDto = userService.createUser(modelMapper.map(user, UserDto.class));
        return userDto.getName();
    }

}
