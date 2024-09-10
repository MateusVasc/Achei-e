package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterCommentResponse(UUID commentId, UUID postId, UUID userId,
                                      LocalDate createdAt) {

}
