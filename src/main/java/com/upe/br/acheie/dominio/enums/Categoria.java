package com.upe.br.acheie.dominio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Categoria {

  ELETRONICO("Eletronico"),
  CHAVES("Chaves");

  private final String valor;
}
