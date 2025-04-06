package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record EndSearchResponse(UUID userId, UUID postId, LocalDate returnedAt) {

}
