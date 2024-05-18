package com.upe.br.acheie.dominio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tipo {

  PERDIDO("Perdido"),
  ENCONTRADO("Encontrado"),
  DEVOLVIDO("Devolvido");

  private final String valor;
}
