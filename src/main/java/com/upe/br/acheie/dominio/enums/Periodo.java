package com.upe.br.acheie.dominio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Periodo {

  PRIMEIRO("1"),
  SEGUNDO("2"),
  TERCEIRO("3"),
  QUARTO("4"),
  QUINTO("5"),
  SEXTO("6"),
  SETIMO("7"),
  OITAVO("8"),
  NONO("9"),
  DECIMO("10"),
  DESPERIORIZADO("Desperiorizado");

  private final String valor;
}
