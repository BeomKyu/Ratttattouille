package com.external.sample.security;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.forrrest.common.security.exception.TokenException;
import com.forrrest.common.security.filter.AbstractTokenFilter;
import com.forrrest.common.security.token.TokenProvider;
import com.forrrest.common.security.token.TokenType;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExternalNonceTokenFilter extends AbstractTokenFilter {

    private static final Logger logger = LoggerFactory.getLogger(ExternalNonceTokenFilter.class);
    private final NonceTokenProvider nonceTokenProvider;

    protected ExternalNonceTokenFilter(@Qualifier("nonceTokenProvider")TokenProvider tokenProvider,
        String[] pathPatterns) {
        super(tokenProvider, pathPatterns);
        this.nonceTokenProvider = (NonceTokenProvider)tokenProvider;
    }

    @Override
    protected TokenType getExpectedTokenType() {
        return TokenType.NONCE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            logger.info("resolveToken");
            if (StringUtils.hasText(token) &&
                nonceTokenProvider.validateToken(token) &&
                nonceTokenProvider.validateTokenType(token, getExpectedTokenType())) {

                // Authentication auth = nonceTokenProvider.getAuthentication(token);
                // SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("do it auth token");
            }
            filterChain.doFilter(request, response);
        } catch (TokenException e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        logger.info("resolveToken");
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            logger.info("resolveToken");
            return bearerToken.substring(7);
        }
        logger.info("resolveToken");
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return false;
    }

}
