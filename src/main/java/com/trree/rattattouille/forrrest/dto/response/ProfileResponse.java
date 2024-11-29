package com.trree.rattattouille.forrrest.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.trree.rattattouille.forrrest.entity.Profile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponse {
    private Long id;
    private String profileName;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ProfileResponse(Long id, String profileName, List<String> roles,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.profileName = profileName;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProfileResponse from(Profile profile) {
        return ProfileResponse.builder()
            .id(profile.getId())
            .profileName(profile.getProfileName())
            .roles(profile.getRoles())
            .createdAt(profile.getCreatedAt())
            .updatedAt(profile.getUpdatedAt())
            .build();
    }
}