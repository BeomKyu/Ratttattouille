package com.trree.rattattouille.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppRequest {

    @NotBlank(message = "clientId는 필수입니다.")
    private String clientId;

    @NotBlank(message = "clientSecret 필수입니다.")
    private String clientSecret;
}
