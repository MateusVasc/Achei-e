package com.upe.br.auth.dtos;

import com.upe.br.auth.domain.enums.Major;
import com.upe.br.auth.domain.enums.Semester;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Sobrenome é obrigatório")
        String lastname,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Senha deve ter no mínimo 8 caracteres, incluindo letras e números")
        String password,

        @NotNull(message = "Curso é obrigatório")
        Major major,

        @NotNull(message = "Semestre é obrigatório")
        Semester semester,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^\\+?[1-9][0-9]{10,14}$",
                message = "Telefone inválido")
        String phone,

        byte[] image
) {
}
