package com.trree.rattattouille.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trree.rattattouille.dto.request.AppRequest;
import com.trree.rattattouille.entity.App;
import com.trree.rattattouille.service.AppService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "App", description = "인증 API")
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
// @SecurityRequirement(name = "bearer-token")
@Slf4j
public class AppController {

    private final AppService appService;

    @Operation(summary = "앱 정보 생성", description = "app 정보 생성")
    @PostMapping("")
    public ResponseEntity<Void> createApp(@Valid @RequestBody AppRequest appRequest) {
        appService.createApp(appRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "앱 return", description = "app 정보 생성")
    @GetMapping("")
    public ResponseEntity<App> readApp(@RequestParam String clientId) {
        return ResponseEntity.ok(appService.getApp(clientId));
    }
}
