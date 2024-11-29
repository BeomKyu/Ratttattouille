package com.trree.rattattouille.forrrest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trree.rattattouille.forrrest.dto.request.AppRequest;
import com.trree.rattattouille.forrrest.dto.response.AppResponse;
import com.trree.rattattouille.forrrest.entity.App;
import com.trree.rattattouille.global.exception.CustomException;
import com.trree.rattattouille.global.exception.ErrorCode;
import com.trree.rattattouille.forrrest.repository.AppRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AppService {

    private final AppRepository appRepository;

    private final Logger logger = LoggerFactory.getLogger(AppService.class);

    public String getClientSecretByClientId(String clientId) {
        log.info("AppService clientId : {}", clientId);
        return appRepository.findByClientId(clientId)
            .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND))
            .getClientSecret();
    }

    @Transactional
    public void createApp(AppRequest appRequest) {
        appRepository.save(
            App.builder()
                .clientId(appRequest.getClientId())
                .clientSecret(appRequest.getClientSecret())
                .build()
        );
    }

    public AppResponse readApp(String clientId) {
        log.info("app service clientId : {}", clientId);
        return AppResponse.from(appRepository.findByClientId(clientId)
            .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND)));
    }
}
