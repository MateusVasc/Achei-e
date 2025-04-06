package com.upe.br.acheie.dtos.request;

import com.upe.br.acheie.domain.enums.Major;
import com.upe.br.acheie.domain.enums.Semester;

public record RegisterRequest(String name, String lastname, String email, String password,
                              Major major, Semester semester, String phone,
                              byte[] image) {

}
