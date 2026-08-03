package com.sang.ecommerce.service.impl;

import com.sang.ecommerce.dto.LoginDTO;
import com.sang.ecommerce.dto.RefreshTokenDTO;
import com.sang.ecommerce.dto.RegisterDTO;
import com.sang.ecommerce.entity.RefreshToken;
import com.sang.ecommerce.entity.User;
import com.sang.ecommerce.exception.AppGlobalException;
import com.sang.ecommerce.exception.ErrorConstant;
import com.sang.ecommerce.repository.RefreshTokenRepository;
import com.sang.ecommerce.repository.UserRepository;
import com.sang.ecommerce.service.AuthService;
import com.sang.ecommerce.service.UserService;
import com.sang.ecommerce.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenExpirationMs;

    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginDTO login(LoginDTO dto) {
        var email = dto.getEmail();
        var currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppGlobalException(ErrorConstant.USER_NOT_FOUND_MSG, ErrorConstant.USER_NOT_FOUND_CODE, HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), currentUser.getPassword())) {
            throw new AppGlobalException(ErrorConstant.INVALID_CREDENTIALS_MSG, ErrorConstant.INVALID_CREDENTIALS_CODE, HttpStatus.UNAUTHORIZED);
        }

        var accessToken = jwtUtils.generateToken(dto.getEmail());
        var refreshToken = UUID.randomUUID().toString();

        var now = Instant.now();

        var newRefreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(currentUser)
                .expiredDate(now.plusMillis(refreshTokenExpirationMs))
                .revoked(false)
                .build();

        refreshTokenRepository.save(newRefreshTokenEntity);

        return LoginDTO.builder()
                .email(dto.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshTokenDTO refreshToken(RefreshTokenDTO dto) {
        var existingToken = refreshTokenRepository.findByToken(dto.getRefreshToken())
                .orElseThrow(() -> new AppGlobalException(ErrorConstant.INVALID_TOKEN_MSG, ErrorConstant.INVALID_TOKEN_CODE, HttpStatus.UNAUTHORIZED));

        var now = Instant.now();

        if (Boolean.TRUE.equals(existingToken.isRevoked()) || now.isAfter(existingToken.getExpiredDate())) {
            throw new AppGlobalException(ErrorConstant.INVALID_TOKEN_MSG, ErrorConstant.INVALID_TOKEN_CODE, HttpStatus.UNAUTHORIZED);
        }

        var currentUser = existingToken.getUser();

        var newAccessToken = Optional.ofNullable(currentUser)
                .map(user -> jwtUtils.generateToken(user.getEmail()))
                .orElseThrow(() -> new AppGlobalException(ErrorConstant.USER_NOT_FOUND_MSG, ErrorConstant.USER_NOT_FOUND_CODE, HttpStatus.NOT_FOUND));

        var newRefreshToken = UUID.randomUUID().toString();

        existingToken.setRevoked(true);
        refreshTokenRepository.save(existingToken);

        var newRefreshTokenEntity = RefreshToken.builder()
                .token(newRefreshToken)
                .user(currentUser)
                .expiredDate(now.plusMillis(refreshTokenExpirationMs))
                .revoked(false)
                .build();

        refreshTokenRepository.save(newRefreshTokenEntity);

        dto.setRefreshToken(newRefreshToken);
        dto.setAccessToken(newAccessToken);

        return dto;
    }

    @Override
    public RegisterDTO register(RegisterDTO dto) {
        var email = dto.getEmail();

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new AppGlobalException(ErrorConstant.USER_ALREADY_EXISTS_MSG, ErrorConstant.USER_ALREADY_EXISTS_CODE , HttpStatus.BAD_REQUEST);
        });

        var hashedPassword = passwordEncoder.encode(dto.getPassword());

        var defaultRole = userService.getDefaultRole();

        var userEntity = User.builder()
                .email(email)
                .password(hashedPassword)
                .username(dto.getUsername())
                .roles(List.of(defaultRole))
                .build();

        userEntity = userRepository.save(userEntity);

        var accessToke = jwtUtils.generateToken(email);
        var refreshToken = UUID.randomUUID().toString();

        var newRefreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(userEntity)
                .expiredDate(Instant.now().plusMillis(refreshTokenExpirationMs))
                .revoked(false)
                .build();

        refreshTokenRepository.save(newRefreshTokenEntity);

        return RegisterDTO.builder()
                .email(email)
                .username(dto.getUsername())
                .accessToken(accessToke)
                .refreshToken(refreshToken)
                .build();
    }
}
