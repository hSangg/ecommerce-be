package com.sang.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    private String email;

    private String password;

    private String accessToken;

    private String refreshToken;
}

