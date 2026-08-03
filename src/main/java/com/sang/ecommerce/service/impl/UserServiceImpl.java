package com.sang.ecommerce.service.impl;

import com.sang.ecommerce.dto.UserDTO;
import com.sang.ecommerce.entity.Role;
import com.sang.ecommerce.entity.enumeration.RoleEnum;
import com.sang.ecommerce.exception.AppGlobalException;
import com.sang.ecommerce.exception.ErrorConstant;
import com.sang.ecommerce.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Override
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new AppGlobalException(ErrorConstant.USER_NOT_FOUND_MSG, ErrorConstant.INVALID_TOKEN_CODE, org.springframework.http.HttpStatus.NOT_FOUND));
    }

    @Override
    public Role getDefaultRole() {
        return roleRepository.findById(RoleEnum.USER.getRoleId())
                .orElseThrow(() -> new AppGlobalException(ErrorConstant.ROLE_NOT_FOUND_MSG, ErrorConstant.ROLE_NOT_FOUND_CODE, org.springframework.http.HttpStatus.NOT_FOUND));
    }
}
