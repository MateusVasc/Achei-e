package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterPostResponse(UUID idPost, UUID idUsuario, LocalDate criacaoDoPost) {
}
