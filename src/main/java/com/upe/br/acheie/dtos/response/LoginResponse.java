package com.upe.br.acheie.dtos.response;

import java.util.UUID;

public record LoginResponse(UUID idUsuario, String token) {

}
