package com.external.sample.security;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forrrest.common.security.authentication.NonceTokenAuthentication;
import com.forrrest.common.security.authentication.ProfileTokenAuthentication;
import com.forrrest.common.security.authentication.UserTokenAuthentication;
import com.forrrest.common.security.exception.ExpiredTokenException;
import com.forrrest.common.security.exception.InvalidSignatureException;
import com.forrrest.common.security.exception.InvalidTokenException;
import com.forrrest.common.security.exception.TokenException;
import com.forrrest.common.security.exception.TokenExceptionType;
import com.forrrest.common.security.token.TokenProvider;
import com.forrrest.common.security.token.TokenType;
import com.forrrest.common.security.userdetails.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component("nonceTokenProvider")
@Slf4j
public class NonceTokenProvider implements TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(NonceTokenProvider.class);

    @Override
    public String createToken(String subject, TokenType tokenType) {
        return null;
    }

    @Override
    public String createToken(String subject, TokenType tokenType, Map<String, Object> claims) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        logger.info("Received request with Authorization header: {}", token);
        try {
            /*
            web request forrest-appmanagementservice /nonce-tokens/validate
             */
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (SignatureException e) {
            throw new InvalidSignatureException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (UnsupportedJwtException e) {
            throw new TokenException(TokenExceptionType.UNSUPPORTED_TOKEN);
        }
    }

    @Override
    public boolean validateTokenType(String token, TokenType expectedType) {
        return true;
        // logger.info("validateTokenType: {}", token);
        // Claims claims = getClaims(token);
        // logger.info("validateTokenType: {}", claims);
        // String tokenType = claims.get("tokenType", String.class);

        // if (tokenType == null) {
        //     throw new TokenException(TokenExceptionType.WRONG_TYPE);
        // }
        //
        // try {
        //     TokenType actualType = TokenType.valueOf(tokenType);
        //     return actualType == expectedType;
        // } catch (IllegalArgumentException e) {
        //     throw new TokenException(TokenExceptionType.WRONG_TYPE);
        // }
    }

    @Override
    public Claims getClaims(String token) {
        return null;
        // try {
        //     String[] parts = token.split("\\.");
        //     if (parts.length < 2) {
        //         throw new InvalidTokenException();
        //     }
        //     logger.info("getClaims parts: {}", (Object)parts);
        //     // JWT의 페이로드 부분을 디코딩
        //     String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        //     logger.info("getClaims payload: {}", payload);
        //
        //     // Jackson ObjectMapper를 사용하여 페이로드를 Map 객체로 변환
        //     ObjectMapper mapper = new ObjectMapper();
        //     Map<String, Object> claimsMap = mapper.readValue(payload, new TypeReference<Map<String, Object>>() {
        //     });
        //     logger.info("getClaims map: {}", claimsMap);
        //
        //     // Map을 Claims 객체로 변환
        //     return new DefaultClaims(claimsMap);
        // } catch (Exception e) {
        //     throw new InvalidTokenException();
        // }
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        if (claims.get("roles") == null) {
            throw new TokenException(TokenExceptionType.EMPTY_CLAIMS);
        }

        CustomUserDetails userDetails = CustomUserDetails.builder()
            .id(claims.getSubject())
            .username(claims.get("username", String.class))
            .roles(claims.get("roles", List.class))
            .enabled(true)
            .build();

        String tokenType = claims.get("tokenType", String.class);
        if (tokenType == null) {
            throw new TokenException(TokenExceptionType.WRONG_TYPE);
        }

        try {
            TokenType type = TokenType.valueOf(tokenType);
            return switch (type) {
                case USER_ACCESS -> new UserTokenAuthentication(userDetails, token);
                case PROFILE_ACCESS -> new ProfileTokenAuthentication(userDetails, token);
                case NONCE -> new NonceTokenAuthentication(userDetails, token);
                default -> throw new TokenException(TokenExceptionType.WRONG_TYPE);
            };
        } catch (IllegalArgumentException e) {
            throw new TokenException(TokenExceptionType.WRONG_TYPE);
        }
    }
}
