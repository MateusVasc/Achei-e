package com.upe.br.acheie.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record TextSearchRequest(@NotBlank String texto, List<String> campos, @Min(1) int limite) {

}
