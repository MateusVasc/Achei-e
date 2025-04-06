package com.upe.br.acheie.dtos.response;

import java.time.LocalDate;

public record RegisterResponse(String email, LocalDate createdAt) {

}
