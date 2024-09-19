package com.upe.br.acheie.domain.dto.request;

import com.upe.br.acheie.domain.enums.Course;
import com.upe.br.acheie.domain.enums.Semester;

public record RegisterRequest(String name, String lastname, String email, String password,
                              Course course, Semester semester, String phone,
                              byte[] photo) {

}
