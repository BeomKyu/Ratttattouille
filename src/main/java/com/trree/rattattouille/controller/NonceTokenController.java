package com.external.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.external.sample.dto.response.UserResponse;
import com.external.sample.service.NonceTokenService;
import com.external.sample.utils.SecurityUtils;

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

    private final Logger logger = LoggerFactory.getLogger(NonceTokenController.class);

    private final NonceTokenService nonceTokenService;

    @Operation(summary = "논스 토큰 검증", description = "nonce token 검증 및 사용자 처리 api")
    @GetMapping("/validate")
    public ResponseEntity<UserResponse> tokenValidate() {
        log.info("Security context : {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("Security context : {}", SecurityUtils.getCurrentNonceProfileId());
        return ResponseEntity.ok(nonceTokenService.validateAndProcessUser());
    }

    @Operation(summary = "test", description = "nonce token 검증 api.")
    @PostMapping("/test")
    public ResponseEntity<Boolean> test() {
        log.info("test SecurityContextHolder.getContext().getAuthentication() : {}", System.identityHashCode(SecurityContextHolder.getContext()));
        return ResponseEntity.ok(true);
    }

}
