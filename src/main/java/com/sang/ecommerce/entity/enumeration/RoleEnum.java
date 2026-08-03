package com.sang.ecommerce.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER(1),
    ADMIN(2);

    private final long roleId;
}
