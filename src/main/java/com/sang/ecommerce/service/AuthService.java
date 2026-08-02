package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.LoginRequestDTO;
import com.sang.ecommerce.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
}

