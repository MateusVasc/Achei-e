package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record DeletePostResponse(LocalDate createdAt, LocalDate deletedAt,
                                 UUID userId) {

}
