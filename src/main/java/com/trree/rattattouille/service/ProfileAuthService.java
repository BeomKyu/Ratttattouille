package com.trree.rattattouille.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forrrest.common.security.authentication.ProfileTokenAuthentication;
import com.forrrest.common.security.config.TokenProperties;
import com.forrrest.common.security.token.JwtTokenProvider;
import com.forrrest.common.security.token.TokenType;
import com.forrrest.common.security.userdetails.CustomUserDetails;
import com.trree.rattattouille.dto.request.TokenRequest;
import com.trree.rattattouille.dto.response.AuthResponse;
import com.trree.rattattouille.dto.response.ProfileResponse;
import com.trree.rattattouille.dto.response.TokenInfo;
import com.trree.rattattouille.entity.Profile;
import com.trree.rattattouille.entity.RefreshToken;
import com.trree.rattattouille.exception.CustomException;
import com.trree.rattattouille.exception.ErrorCode;
import com.trree.rattattouille.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProfileAuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;
    private final TokenProperties tokenProperties;

    @Transactional
    public AuthResponse refreshToken(TokenRequest request) {
        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        if (!refreshTokenRepository.existsByRefreshToken(request.getRefreshToken())) {
            throw new CustomException(ErrorCode.PROFILE_TOKEN_INVALID);
        }

        ProfileTokenAuthentication authentication = (ProfileTokenAuthentication)jwtTokenProvider.getAuthentication(request.getRefreshToken());
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Profile profile = profileService.getProfileById(Long.valueOf(userDetails.getId()));
        refreshTokenRepository.deleteById(profile.getId().toString());

        Map<String, Object> profileClaims = Map.of(
            "username", profile.getProfileName(),
            "roles", List.of("PROFILE")
        );

        String profileAccessToken = jwtTokenProvider.createToken(
            String.valueOf(profile.getId()),
            TokenType.PROFILE_ACCESS,
            profileClaims
        );
        String profileRefreshToken = jwtTokenProvider.createToken(
            String.valueOf(profile.getId()),
            TokenType.PROFILE_REFRESH,
            profileClaims
        );

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
}
