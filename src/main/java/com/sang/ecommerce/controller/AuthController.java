package com.sang.ecommerce.controller;

import com.sang.ecommerce.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public String login(@RequestParam("email") String email) {
        return jwtUtils.generateToken(email);
    }
}
