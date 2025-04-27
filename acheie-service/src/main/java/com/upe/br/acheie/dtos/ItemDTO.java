package com.upe.br.acheie.dtos;

import com.upe.br.acheie.domain.enums.Category;
import com.upe.br.acheie.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ItemDTO(
        UUID id,
        String title,
        String description,
        Status status,
        Category category,
        byte[] image,
        UUID createdBy,
        LocalDateTime createdAt,
        LocalDateTime lostAt,
        LocalDateTime foundAt,
        LocalDateTime returnedAt,
        List<CommentDTO> comments
) {
}
