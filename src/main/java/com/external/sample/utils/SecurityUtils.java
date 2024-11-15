package com.external.sample.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.external.sample.security.ExternalNonceTokenFilter;
import com.forrrest.common.security.authentication.NonceTokenAuthentication;
import com.forrrest.common.security.authentication.ProfileTokenAuthentication;
import com.forrrest.common.security.userdetails.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtils {

    private final Logger logger = LoggerFactory.getLogger(ExternalNonceTokenFilter.class);
    
    public static Long getCurrentProfileId() {
        ProfileTokenAuthentication authentication = (ProfileTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return Long.parseLong(userDetails.getId());
    }

    public static String getCurrentProfileName() {
        ProfileTokenAuthentication authentication = (ProfileTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    public static Long getCurrentNonceProfileId() {
        NonceTokenAuthentication authentication = (NonceTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return Long.parseLong(userDetails.getId());
    }

    public static String getCurrentNonceProfileName() {
        NonceTokenAuthentication authentication = (NonceTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    public static List<String> getCurrentNonceProfileRole() {
        NonceTokenAuthentication authentication = (NonceTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getRoles();
    }

    // public static void validateAppOwnership(App app) {
    //     Long currentProfileId = getCurrentProfileId();
    //     if (!app.getProfileId().equals(currentProfileId)) {
    //         throw new UnauthorizedAccessException("해당 앱에 대한 접근 권한이 없습니다.");
    //     }
    // }
}
