package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;

public record DeleteAccountResponse(String email, LocalDate createdAt, LocalDate deletedAt) {

}
