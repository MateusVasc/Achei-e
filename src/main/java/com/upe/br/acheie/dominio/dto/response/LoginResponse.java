package com.upe.br.acheie.dominio.dto.response;

import java.util.UUID;

public record LoginResponse(UUID idUsuario, String token) {

}
