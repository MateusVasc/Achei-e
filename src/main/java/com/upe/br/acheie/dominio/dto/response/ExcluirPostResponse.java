package com.upe.br.acheie.dominio.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ExcluirPostResponse(LocalDate dataCriacao, LocalDate dataRemocao,
                                  UUID idUsuario) {

}
