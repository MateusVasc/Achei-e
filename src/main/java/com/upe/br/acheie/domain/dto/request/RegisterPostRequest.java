package com.upe.br.acheie.domain.dto.request;

import com.upe.br.acheie.domain.dto.ItemDto;
import com.upe.br.acheie.domain.enums.Type;

public record RegisterPostRequest(Type type, ItemDto item) {

}
