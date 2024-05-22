package com.upe.br.acheie.dominio.dto.response;

import java.time.LocalDate;

public record CadastroResponse(String email, LocalDate criacaoDaConta) {

}
