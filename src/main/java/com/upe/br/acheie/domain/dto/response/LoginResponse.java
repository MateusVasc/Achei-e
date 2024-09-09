package com.upe.br.acheie.domain.dto.response;

import java.util.UUID;

public record LoginResponse(UUID idUsuario, String token) {

}
