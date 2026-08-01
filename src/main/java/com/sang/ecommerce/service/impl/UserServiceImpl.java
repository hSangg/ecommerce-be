package com.sang.ecommerce.service.impl;

import com.sang.ecommerce.dto.UserDTO;
import com.sang.ecommerce.repository.UserRepository;
import com.sang.ecommerce.service.UserService;
import com.sang.ecommerce.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
//                TODO: handle global exception
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
