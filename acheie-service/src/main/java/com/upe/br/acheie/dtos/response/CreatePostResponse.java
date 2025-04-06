package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record CreatePostResponse(UUID postId, UUID userId, LocalDate createdAt) {
}
