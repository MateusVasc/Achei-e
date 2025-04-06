package com.upe.br.acheie.dtos.request;

import com.upe.br.acheie.domain.enums.Type;
import com.upe.br.acheie.dtos.ItemDto;

public record CadastrarPostRequest(Type type, ItemDto item) {

}
