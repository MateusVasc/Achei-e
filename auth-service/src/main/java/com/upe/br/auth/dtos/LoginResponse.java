package com.upe.br.auth.dtos;

import java.util.UUID;

public record LoginResponse(
        UUID id,
        String token
) {
}
