package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

  PERDIDO("Perdido"),
  ENCONTRADO("Encontrado"),
  DEVOLVIDO("Devolvido");

  private final String value;
  
  public static boolean eEstado(String palavra) {
	  Status[] statuses = Status.values();
	  for (Status status : statuses) {
		  if (status.value.equals(palavra)) {
			  return true;
		  }
	  }
	  return false;
  }
}
