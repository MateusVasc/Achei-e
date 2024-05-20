package com.upe.br.acheie.dominio.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tipo { //dá para colocar só no estado

  PERDIDO("Perdido"),
  ENCONTRADO("Encontrado"),
  DEVOLVIDO("Devolvido");

  private final String valor;
}
