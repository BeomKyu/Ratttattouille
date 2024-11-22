package com.trree.rattattouille.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forrrest.common.security.config.TokenProperties;
import com.forrrest.common.security.token.JwtTokenProvider;
import com.forrrest.common.security.token.TokenType;
import com.trree.rattattouille.dto.request.ProfileUpdateRequest;
import com.trree.rattattouille.dto.response.AuthResponse;
import com.trree.rattattouille.dto.response.ProfileResponse;
import com.trree.rattattouille.dto.response.TokenInfo;
import com.trree.rattattouille.entity.Profile;
import com.trree.rattattouille.entity.RefreshToken;
import com.trree.rattattouille.repository.ProfileRepository;
import com.trree.rattattouille.repository.RefreshTokenRepository;
import com.trree.rattattouille.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NonceTokenService {
    
    private final ProfileRepository profileRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenProperties tokenProperties;

    /**
     * 논스 토큰으로부터 사용자 정보를 검증하고 처리
     * 1. 토큰에서 사용자 정보 추출
     * 2. DB에서 사용자 조회
     * 3. 없으면 새로 생성, 있으면 기존 사용자 반환
     */
    @Transactional
    public AuthResponse validateAndProcessProfile() {
        // SecurityContext에서 사용자 정보 추출
        Long profileId = SecurityUtils.getCurrentNonceProfileId();
        String profileName = SecurityUtils.getCurrentNonceProfileName();
        List<String> roles = SecurityUtils.getCurrentNonceProfileRole();

        log.info("Processing Profile from nonce token - ProfileId: {}, ProfileName: {}", profileId, profileName);

        Profile profile = profileRepository.findById(profileId)
            .orElseGet(() -> createNewProfile(profileId, profileName, roles));

        Map<String, Object> profileClaims = Map.of(
            "username",profile.getProfileName(),
            "roles", profile.getRoles()
        );

        String profileAccessToken = jwtTokenProvider.createToken(profile.getId().toString(), TokenType.PROFILE_ACCESS, profileClaims);
        String profileRefreshToken = jwtTokenProvider.createToken(profile.getId().toString(), TokenType.PROFILE_REFRESH, profileClaims);

        refreshTokenRepository.save(
            new RefreshToken(profile.getId().toString(), profileRefreshToken, LocalDateTime.now()));

        return AuthResponse.builder()
            .profileToken(
                TokenInfo.builder()
                    .accessToken(profileAccessToken)
                    .refreshToken(profileRefreshToken)
                    .tokenType("Bearer")
                    .expiresIn(tokenProperties.getValidity().get(TokenType.PROFILE_ACCESS))
                    .build()
            )
            .profileResponse(ProfileResponse.from(profile))
            .build();
    }

    /**
     * 새로운 사용자 생성
     */
    private Profile createNewProfile(Long profileId, String profileName, List<String> roles) {
        log.info("Creating new Profile - ProfileId: {}", profileId);

        Profile newProfile = Profile.builder()
            .id(profileId)
            .profileName(profileName)
            .roles(roles)
            .build();
            
        return profileRepository.save(newProfile);
    }

    /**
     * 사용자 정보 업데이트 (필요한 경우 추가)
     */
    @Transactional
    public ProfileResponse updateProfile(Long profileId, ProfileUpdateRequest request) {
        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(() -> new IllegalArgumentException("profile not found - id: " + profileId));

        // 업데이트 로직 구현 (엔티티에 수정 메서드 추가 필요)
        
        return ProfileResponse.from(profile);
    }

    /**
     * 사용자 존재 여부 확인
     */
    public boolean existsProfile(Long profileId) {
        return profileRepository.existsById(profileId);
    }
}