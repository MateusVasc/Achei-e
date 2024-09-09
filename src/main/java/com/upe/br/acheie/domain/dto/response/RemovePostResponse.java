package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record RemovePostResponse(LocalDate dataCriacao, LocalDate dataRemocao,
                                 UUID idUsuario) {

}
