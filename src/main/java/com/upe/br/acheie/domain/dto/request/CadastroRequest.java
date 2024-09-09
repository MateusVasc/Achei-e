package com.upe.br.acheie.domain.dto.request;

import com.upe.br.acheie.domain.enums.Curso;
import com.upe.br.acheie.domain.enums.Periodo;

public record CadastroRequest(String nome, String sobrenome, String email, String senha,
                              Curso curso, Periodo periodo, String telefone,
                              byte[] foto) {

}
