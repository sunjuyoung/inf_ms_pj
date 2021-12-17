package com.example.userservice.controller;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.ResponseUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UsersController {

    private final Environment env;
    private final UserService userService;
    private final ModelMapper modelMapper;

/*    @Value("${greeting.message}")
    private String welcomeMessage;*/

    @GetMapping("/health_check")
    public String status(){
        return String.format("working...port : %s",env.getProperty("local.server.port"));
    }
    @GetMapping("/welcome")
    public String welcome(){
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody RequestUser user){
        UserDto dto = modelMapper.map(user, UserDto.class);
        UserDto user1 = userService.createUser(dto);
        ResponseUser responseUser = modelMapper.map(user1, ResponseUser.class);
        return ResponseEntity.ok().body(responseUser);
    }

}
