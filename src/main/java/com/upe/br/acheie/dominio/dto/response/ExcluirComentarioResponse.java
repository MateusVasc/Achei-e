package com.upe.br.acheie.dominio.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ExcluirComentarioResponse(LocalDate dataCriacao, LocalDate dataRemocao,
                                        UUID idUsuario, UUID idPost) {

}
