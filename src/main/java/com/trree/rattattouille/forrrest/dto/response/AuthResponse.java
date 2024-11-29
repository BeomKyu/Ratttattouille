package com.trree.rattattouille.forrrest.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private TokenInfo profileToken;
    private ProfileResponse profileResponse;
}