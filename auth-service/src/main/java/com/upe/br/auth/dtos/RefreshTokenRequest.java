package com.upe.br.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        String accessToken,

        @NotBlank
        String refreshToken
) {
}
