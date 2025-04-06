package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record CreateCommentResponse(UUID commentId, UUID postId, UUID userId,
                                    LocalDate createdAt) {

}
