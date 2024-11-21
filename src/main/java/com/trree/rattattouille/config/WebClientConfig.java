package com.trree.rattattouille.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    String forrrest_app_management_service_url;

    public WebClientConfig(@Value("${security.token.app-management-server-paths}")String forrrest_app_management_service_url) {
        this.forrrest_app_management_service_url = forrrest_app_management_service_url;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl(forrrest_app_management_service_url)  // 실제 인증 서버 URL로 변경
            .build();
    }
}