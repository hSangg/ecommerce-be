package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.UserDTO;

public interface UserService {
    UserDTO findByEmail(String email);
}

