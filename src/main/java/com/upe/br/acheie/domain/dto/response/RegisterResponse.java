package com.upe.br.acheie.domain.dto.response;

import java.time.LocalDate;

public record RegisterResponse(String email, LocalDate criacaoDaConta) {

}
