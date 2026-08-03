package com.sang.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String accessToken;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String refreshToken;
}

