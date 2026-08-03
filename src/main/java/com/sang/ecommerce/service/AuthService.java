package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.LoginDTO;
import com.sang.ecommerce.dto.RefreshTokenDTO;
import com.sang.ecommerce.dto.RegisterDTO;

public interface AuthService {
    LoginDTO login(LoginDTO loginRequest);

    RefreshTokenDTO refreshToken(RefreshTokenDTO refreshTokenRequest);

    RegisterDTO register(RegisterDTO dto);
}

