package com.external.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.external.sample.dto.request.UserUpdateRequest;
import com.external.sample.dto.response.UserResponse;
import com.external.sample.entity.User;
import com.external.sample.repository.UserRepository;
import com.external.sample.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NonceTokenService {
    
    private final UserRepository userRepository;

    /**
     * 논스 토큰으로부터 사용자 정보를 검증하고 처리
     * 1. 토큰에서 사용자 정보 추출
     * 2. DB에서 사용자 조회
     * 3. 없으면 새로 생성, 있으면 기존 사용자 반환
     */
    @Transactional
    public UserResponse validateAndProcessUser() {
        // SecurityContext에서 사용자 정보 추출
        Long userId = SecurityUtils.getCurrentNonceProfileId();
        String username = SecurityUtils.getCurrentNonceProfileName();
        List<String> roles = SecurityUtils.getCurrentNonceProfileRole();

        log.info("Processing user from nonce token - userId: {}, username: {}", userId, username);

        User user = userRepository.findById(userId)
            .orElseGet(() -> createNewUser(userId, username, roles));
        
        return UserResponse.from(user);
    }

    /**
     * 새로운 사용자 생성
     */
    private User createNewUser(Long userId, String username, List<String> roles) {
        log.info("Creating new user - userId: {}", userId);
        
        User newUser = User.builder()
            .id(userId)
            .username(username)
            .roles(roles)
            .build();
            
        return userRepository.save(newUser);
    }

    /**
     * 사용자 정보 업데이트 (필요한 경우 추가)
     */
    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found - id: " + userId));

        // 업데이트 로직 구현 (엔티티에 수정 메서드 추가 필요)
        
        return UserResponse.from(user);
    }

    /**
     * 사용자 존재 여부 확인
     */
    public boolean existsUser(Long userId) {
        return userRepository.existsById(userId);
    }
}