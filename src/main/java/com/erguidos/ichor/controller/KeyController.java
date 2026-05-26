package com.erguidos.ichor.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.PublicKeyResponse;
import com.erguidos.ichor.service.key.KeyServiceInterface;

@RestController
@RequestMapping("/api/v1/keys")
@CrossOrigin(origins = "http://localhost:4200")
public class KeyController {
    private final KeyServiceInterface keyService;
    
    public KeyController(KeyServiceInterface keyService) {
        this.keyService = keyService;
    }
    
    @GetMapping("/public-key")
    public ResponseEntity<PublicKeyResponse> getPublicKey() {
        return ResponseEntity.ok()
                .cacheControl(
                    CacheControl.maxAge(24, TimeUnit.HOURS)
                                .cachePublic()
                )
                .body(new PublicKeyResponse(keyService.getPublicKey()));
    }
}
