package com.upe.br.auth.controllers;

import com.upe.br.auth.dtos.LoginRequest;
import com.upe.br.auth.dtos.LoginResponse;
import com.upe.br.auth.dtos.RefreshTokenRequest;
import com.upe.br.auth.dtos.RegisterRequest;
import com.upe.br.auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        this.authService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(String refreshToken) {
        this.authService.logout(refreshToken);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
