package com.upe.br.acheie.domain.dto.response;

import java.util.UUID;

public record LoginResponse(UUID userId, String token) {

}
