package com.upe.br.acheie.dominio.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Curso {

  ENGENHARIA_DE_SOFTWARE("Engenharia de Software"),
  LICENCIATURA_EM_COMPUTACAO("Licenciatura em Computação"),
  MEDICINA("Medicina"),
  PSICOLOGIA("Psicologia"),
  LICENCIATURA_EM_HISTORIA("Licenciatura em Historia"),
  LICENCIATURA_EM_GEOGRAFIA("Licenciatura em Geografia"),
  LICENCIATURA_EM_CIENCIAS_BIOLOGICAS("Licenciatura em Ciencias Biologicas"),
  LICENCIATURA_EM_LETRAS("Licenciatura em Letras"),
  LICENCIATURA_EM_PEDAGOGIA("Licenciatura em Pedagogia");
  private final String valor;
}
