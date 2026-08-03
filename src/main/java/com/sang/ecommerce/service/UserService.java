package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.UserDTO;
import com.sang.ecommerce.entity.Role;

public interface UserService {
    UserDTO findByEmail(String email);
    Role getDefaultRole();
}

