package com.upe.br.acheie.dtos.request;

import com.upe.br.acheie.domain.enums.Type;
import com.upe.br.acheie.dtos.ItemDTO;

public record CreatePostRequest(Type type, ItemDTO item) {

}
