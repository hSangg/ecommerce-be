package com.sang.ecommerce.controller;

import com.sang.ecommerce.dto.LoginDTO;
import com.sang.ecommerce.dto.RefreshTokenDTO;
import com.sang.ecommerce.dto.RegisterDTO;
import com.sang.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        var result = authService.register(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginRequest) {
        var result = authService.login(loginRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenRequest) {
        var result = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(result);
    }
}
