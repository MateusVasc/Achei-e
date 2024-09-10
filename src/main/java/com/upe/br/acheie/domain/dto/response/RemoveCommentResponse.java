package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record RemoveCommentResponse(LocalDate createdAt, LocalDate removedAt,
                                    UUID userId, UUID postId) {

}
