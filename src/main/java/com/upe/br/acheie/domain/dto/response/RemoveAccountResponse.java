package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;

public record RemoveAccountResponse(String email, LocalDate dataDeCriacao, LocalDate dataDeRemocao) {

}
