package com.upe.br.acheie.dominio.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record EncerrarProcuraResponse(UUID idUsuario, UUID idPost, LocalDate dataDevolucao) {

}
