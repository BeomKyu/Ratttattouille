package com.trree.rattattouille.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRequest {
    
    @NotBlank(message = "리프레시 토큰은 필수입니다.")
    private String refreshToken;
} 