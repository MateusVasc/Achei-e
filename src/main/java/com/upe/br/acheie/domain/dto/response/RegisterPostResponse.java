package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterPostResponse(UUID postId, UUID userId, LocalDate createdAt) {
}
