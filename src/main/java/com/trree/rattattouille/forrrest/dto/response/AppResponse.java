package com.trree.rattattouille.forrrest.dto.response;

import com.trree.rattattouille.forrrest.entity.App;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppResponse {
    private String clientId;
    private String clientSecret;

    @Builder
    public AppResponse(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public static AppResponse from(App app) {
        return AppResponse.builder()
            .clientId(app.getClientId())
            .clientSecret(app.getClientSecret())
            .build();
    }
}
