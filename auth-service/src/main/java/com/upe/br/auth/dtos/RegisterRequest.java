package com.upe.br.auth.dtos;

import com.upe.br.auth.domain.enums.Major;
import com.upe.br.auth.domain.enums.Semester;
import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "Name is a required field")
        @Size(min = 2, max = 50, message = "Name must  have 2 to 50 letters")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Name must contain only letters")
        String name,

        @NotBlank(message = "Lastname is a required field")
        @Size(min = 2, max = 50, message = "Lastname must  have 2 to 50 letters")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Lastname must contain only letters")
        String lastname,

        @NotBlank(message = "Email is a required field")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Password is a required field")
        @Size(min = 8, message = "Password must have at least 8 chars")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password must include an uppercase letter, a lowercase letter, a number and a special char")
        String password,

        @NotNull(message = "Major is a required field")
        Major major,

        @NotNull(message = "Semester is a required field")
        Semester semester,

        @NotBlank(message = "Phone is a required field")
        @Pattern(regexp = "^\\+?[1-9][0-9]{10,14}$",
                message = "Invalid phone number")
        String phone,

        byte[] image
) {
}
