package com.erguidos.ichor.controller;

import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.response.PublicKeyResponse;
import com.erguidos.ichor.service.KeyServiceInterface;

@RestController
@RequestMapping("/api/v1/keys")
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
    
    @PostMapping("/decrypt")
    public ResponseEntity<?> decrypt(@RequestBody DecryptRequest request) {
        try {
            return ResponseEntity.ok(this.keyService.decrypt(request.data()));
        } catch (GeneralSecurityException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
