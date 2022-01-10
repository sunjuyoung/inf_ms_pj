package com.example.userservice.service;

import com.example.userservice.dto.ResponseOrder;
import com.example.userservice.dto.ResponseUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;
    private final RestTemplate restTemplate;

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        UserEntity save = userRepository.save(userEntity);
        UserDto responseDto = getUserDto(save);
        return responseDto;
    }

    private UserDto getUserDto(UserEntity save) {
        return modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null){
            throw new UsernameNotFoundException("User not found");
        }
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        /*RestTemplate*/
        String orderUrl = String.format(env.getProperty("order-service.url"),userId);
        //List<ResponseOrder> orders = new ArrayList<>();
        ResponseEntity<List<ResponseOrder>> orderListResponse =
                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<ResponseOrder>>() {

                         });

        List<ResponseOrder> orderList = orderListResponse.getBody();
        userDto.setOrders(orderList);
        return userDto;
    }

    @Override
    public List<UserDto> getUserByAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> dtoList = new ArrayList<>();
        if(userEntities != null){
            userEntities.forEach(i->{
                dtoList.add(modelMapper.map(i,UserDto.class));
            });
        }
        return dtoList;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException("이메일 존재하지 않습니다.");
        }
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        return new User(userEntity.getEmail(),userEntity.getEncryptedPwd(),
                true,true,true,true,
                new ArrayList<>());
    }
}
