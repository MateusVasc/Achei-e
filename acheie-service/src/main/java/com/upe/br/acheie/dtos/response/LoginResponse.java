package com.upe.br.acheie.dtos.response;

import java.util.UUID;

public record LoginResponse(UUID userId, String token) {

}
