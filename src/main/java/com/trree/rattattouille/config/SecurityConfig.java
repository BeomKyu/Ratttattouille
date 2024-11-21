package com.trree.rattattouille.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.forrrest.common.security.filter.UserTokenFilter;
import com.trree.rattattouille.security.NonceTokenAuthenticationFilter;
import com.forrrest.common.security.filter.ExternalNonceTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ExternalNonceTokenFilter externalNonceTokenFilter;

    private final NonceTokenAuthenticationFilter nonceTokenAuthenticationFilter;

    // private final UserTokenFilter userTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/nonce-tokens/**").hasRole("PROFILE")
                .anyRequest().denyAll()
            )
            .addFilterBefore(externalNonceTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(nonceTokenAuthenticationFilter, ExternalNonceTokenFilter.class);
        
        return http.build();
    }

} 