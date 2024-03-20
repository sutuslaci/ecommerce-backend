package com.ecommerce.auth.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {
    private String accessToken;
}
