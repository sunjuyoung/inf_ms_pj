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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
//@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UsersController {

    private final Environment env;
    private final UserService userService;
    private final ModelMapper modelMapper;

/*    @Value("${greeting.message}")
    private String welcomeMessage;*/

    @GetMapping("/health_check")
    public String status(){
        StringBuilder sb = new StringBuilder();
        sb.append("local.port : "+env.getProperty("local.server.port"));
        sb.append(" server.port : "+env.getProperty("server.port"));
        sb.append(" token.secret : "+env.getProperty("token.secret"));
        sb.append(" token expiration time : "+env.getProperty("token.expiration_time"));
        return sb.toString();
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

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUserAll(){
        List<UserDto> dtoList = userService.getUserByAll();
        List<ResponseUser> responseUserList = new ArrayList<>();
        dtoList.forEach(i->{
            responseUserList.add(modelMapper.map(i,ResponseUser.class));
        });
        return ResponseEntity.ok().body(responseUserList);

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser responseUser = modelMapper.map(userDto,ResponseUser.class);
        return ResponseEntity.ok().body(responseUser);
    }

}
