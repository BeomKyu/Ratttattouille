package com.trree.rattattouille.global.security;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import com.trree.rattattouille.global.exception.WebClientResponseException;
import com.trree.rattattouille.forrrest.service.AppService;
import com.trree.rattattouille.global.utils.SecurityUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NonceTokenAuthenticationFilter extends OncePerRequestFilter {

    private final String[] pathPatterns;

    private final WebClient webClient;

    private final AppService appService;

    private final Logger logger = LoggerFactory.getLogger(NonceTokenAuthenticationFilter.class);

    public NonceTokenAuthenticationFilter(@Value("${security.token.external-nonce-paths}")String[] pathPatterns, WebClient webClient, AppService appService) {
        this.pathPatterns = pathPatterns;
        this.webClient = webClient;
        this.appService = appService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        String clientSecret = appService.getClientSecretByClientId(SecurityUtils.getCurrentClientId());
        log.info("NonceTokenAuthenticationFilter clientSecret : {}", clientSecret);
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없습니다.");
            return;
        }
        try {
            webClient.post()
                .uri("/nonce-tokens/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("token", token, "clientSecret", clientSecret))
                .retrieve()
                .toBodilessEntity()  // 응답 바디 없이 상태 코드만 확인
                .block(Duration.ofSeconds(3));
            filterChain.doFilter(request, response);
        } catch (WebClientResponseException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 검증 실패");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return Arrays.stream(pathPatterns)
            .noneMatch(path::startsWith);
    }
}