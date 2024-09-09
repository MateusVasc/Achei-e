package com.upe.br.acheie.domain.dto.request;

import com.upe.br.acheie.domain.enums.Course;
import com.upe.br.acheie.domain.enums.Semester;

public record RegisterRequest(String nome, String sobrenome, String email, String senha,
                              Course course, Semester semester, String telefone,
                              byte[] foto) {

}
