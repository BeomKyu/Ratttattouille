package com.trree.rattattouille.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trree.rattattouille.dto.request.TestRequest;
import com.trree.rattattouille.dto.request.TokenRequest;
import com.trree.rattattouille.dto.response.AuthResponse;
import com.trree.rattattouille.service.ProfileAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Profile-tokens", description = "인증 API")
@RestController
@RequestMapping("/profile-tokens")
@RequiredArgsConstructor
// @SecurityRequirement(name = "bearer-token")
@Slf4j
public class ProfileTokenController {

    private final ProfileAuthService profileAuthService;

    @Operation(summary = "profile 토큰 리프레시", description = "profile token refresh")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody TokenRequest request) {
        log.info("profile token controller request : {}", request.getRefreshToken());
        return ResponseEntity.ok(profileAuthService.refreshToken(request));
    }

    @Operation(summary = "profile controller test", description = "test")
    @PostMapping("/test")
    public ResponseEntity<Boolean> test(@Valid @RequestBody TestRequest testRequest) {
        log.info("profile controller test : {}", testRequest);
        return ResponseEntity.ok(true);
    }
}