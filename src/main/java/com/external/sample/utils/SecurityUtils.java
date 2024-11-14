package com.external.sample.utils;

import org.springframework.security.core.context.SecurityContextHolder;

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

    // public static void validateAppOwnership(App app) {
    //     Long currentProfileId = getCurrentProfileId();
    //     if (!app.getProfileId().equals(currentProfileId)) {
    //         throw new UnauthorizedAccessException("해당 앱에 대한 접근 권한이 없습니다.");
    //     }
    // }
}
