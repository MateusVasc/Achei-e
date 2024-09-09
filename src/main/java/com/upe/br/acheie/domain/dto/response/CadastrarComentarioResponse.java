package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record CadastrarComentarioResponse(UUID idComentario, UUID idPost, UUID idUsuario,
                                          LocalDate criacaoDoComentario) {

}
