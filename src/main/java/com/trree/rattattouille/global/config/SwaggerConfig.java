package com.trree.rattattouille.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI commonOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Rattattouille Service API")
                        .description("Rattattouille Service API 명세서")
                        .version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi rattattouilleApi() {
        return GroupedOpenApi.builder()
                .group("rattattouille-api")
                .packagesToScan("com.trree.rattattouille.controller")
                .pathsToExclude("/app/**", "/nonce-tokens/**", "/profile-tokens/**")  // forrrest 관련 API 제외
                .build();
    }

    @Bean
    public GroupedOpenApi forrrestApi() {
        return GroupedOpenApi.builder()
                .group("forrrest-api")
                .packagesToScan("com.trree.rattattouille.forrrest")
                .pathsToMatch("/app/**", "/nonce-tokens/**", "/profile-tokens/**")    // forrrest 관련 API만 포함
                .build();
    }
}