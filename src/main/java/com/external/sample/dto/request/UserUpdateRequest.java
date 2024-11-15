package com.external.sample.dto.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {
    private String username;
    private List<String> roles;

    @Builder
    public UserUpdateRequest(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }
}
