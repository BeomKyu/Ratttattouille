package com.trree.rattattouille.dto.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileUpdateRequest {
    private String profileName;
    private List<String> roles;

    @Builder
    public ProfileUpdateRequest(String profileName, List<String> roles) {
        this.profileName = profileName;
        this.roles = roles;
    }
}
