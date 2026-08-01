package com.sang.ecommerce.service.mapper;

import com.sang.ecommerce.dto.UserDTO;
import com.sang.ecommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User product);
}

