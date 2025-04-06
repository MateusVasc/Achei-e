package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;
import java.util.UUID;

public record CadastroPostResponse(UUID idPost, UUID idUsuario, LocalDate criacaoDoPost) {
}
