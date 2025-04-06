package com.upe.br.acheie.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record SearchByTextRequest(@NotBlank String text, List<String> fields, @Min(1) int limit) {

}
