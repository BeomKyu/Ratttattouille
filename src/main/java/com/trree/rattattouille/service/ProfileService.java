package com.trree.rattattouille.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trree.rattattouille.entity.Profile;
import com.trree.rattattouille.exception.CustomException;
import com.trree.rattattouille.exception.ErrorCode;
import com.trree.rattattouille.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile getProfileById(Long id){
        return profileRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));
    }

}
