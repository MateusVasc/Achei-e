package com.upe.br.acheie.dtos.request;

import com.upe.br.acheie.domain.enums.Major;
import com.upe.br.acheie.domain.enums.Semester;

public record CadastroRequest(String nome, String sobrenome, String email, String senha,
                              Major major, Semester semester, String telefone,
                              byte[] foto) {

}
