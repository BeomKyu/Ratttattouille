package com.trree.rattattouille.forrrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trree.rattattouille.forrrest.dto.response.AuthResponse;
import com.trree.rattattouille.forrrest.service.NonceTokenService;
import com.trree.rattattouille.global.utils.SecurityUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Nonce-tokens", description = "인증 API")
@RestController
@RequestMapping("/nonce-tokens")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token")
@Slf4j
public class NonceTokenController {

    private final NonceTokenService nonceTokenService;

    @Operation(summary = "논스 토큰 검증", description = "nonce token 검증 및 사용자 처리 api")
    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> tokenValidate() {
        return ResponseEntity.ok(nonceTokenService.validateAndProcessProfile());
    }

    @Operation(summary = "test", description = "nonce token 검증 api.")
    @PostMapping("/test")
    public ResponseEntity<Boolean> test() {
        log.info("test SecurityContextHolder.getContext().getAuthentication() : {}", System.identityHashCode(SecurityContextHolder.getContext()));
        log.info("test profile roles : {}", SecurityUtils.getCurrentNonceProfileRole());
        return ResponseEntity.ok(true);
    }

}
