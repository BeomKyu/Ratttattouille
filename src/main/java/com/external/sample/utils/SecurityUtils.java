package com.external.sample.utils;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.forrrest.common.security.authentication.NonceTokenAuthentication;
import com.forrrest.common.security.authentication.ProfileTokenAuthentication;
import com.forrrest.common.security.userdetails.CustomUserDetails;

public class SecurityUtils {
    
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
