package com.upe.br.acheie.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDTO(
        UUID id,
        String subject,
        UUID createdBy,
        LocalDateTime createdAt
) {
}
