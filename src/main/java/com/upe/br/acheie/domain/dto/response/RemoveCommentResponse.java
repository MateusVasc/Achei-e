package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record RemoveCommentResponse(LocalDate dataCriacao, LocalDate dataRemocao,
                                    UUID idUsuario, UUID idPost) {

}
