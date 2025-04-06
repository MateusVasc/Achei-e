package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;

public record ExcluirContaResponse(String email, LocalDate dataDeCriacao, LocalDate dataDeRemocao) {

}
