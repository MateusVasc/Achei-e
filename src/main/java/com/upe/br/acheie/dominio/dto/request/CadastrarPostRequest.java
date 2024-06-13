package com.upe.br.acheie.dominio.dto.request;

import com.upe.br.acheie.dominio.dto.ItemDto;
import com.upe.br.acheie.dominio.utils.enums.Tipo;

public record CadastrarPostRequest(Tipo tipo, ItemDto item) {

}
