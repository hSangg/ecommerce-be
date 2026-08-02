package com.sang.ecommerce.service.impl;

import com.sang.ecommerce.dto.LoginRequestDTO;
import com.sang.ecommerce.dto.LoginResponseDTO;
import com.sang.ecommerce.service.AuthService;
import com.sang.ecommerce.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        // TODO: add verify password
        log.info("Login request: {}", dto);

        var accessToken = jwtUtils.generateToken(dto.getEmail());

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
