package com.upe.br.acheie.domain.dto.request;

import com.upe.br.acheie.domain.dto.ItemDto;
import com.upe.br.acheie.domain.enums.Tipo;

public record CadastrarPostRequest(Tipo tipo, ItemDto item) {

}
