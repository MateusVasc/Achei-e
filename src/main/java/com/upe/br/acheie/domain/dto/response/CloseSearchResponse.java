package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record CloseSearchResponse(UUID idUsuario, UUID idPost, LocalDate dataDevolucao) {

}
