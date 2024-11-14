package com.external.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.external.sample.security.ExternalNonceTokenFilter;
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

    private static final Logger logger = LoggerFactory.getLogger(NonceTokenController.class);

    @Operation(summary = "논스 토큰 검증", description = "nonce token 검증 api.")
    @PostMapping("/validate")
    public ResponseEntity<Boolean> tokenValidate() {
        logger.info("validate controller");
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "test", description = "nonce token 검증 api.")
    @PostMapping("/test")
    public ResponseEntity<Boolean> test() {
        logger.info("test controller");
        return ResponseEntity.ok(true);
    }

}
