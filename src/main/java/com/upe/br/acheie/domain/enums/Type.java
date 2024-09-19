package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

  PERDIDO("Perdido"),
  ENCONTRADO("Encontrado"),
  DEVOLVIDO("Devolvido");

  private final String value;
  
  public static boolean eTipo(String palavra) {
	  Type[] types = Type.values();
	  for (Type type : types) {
		  if (type.value.equals(palavra)) {
			  return true;
		  }
	  }
	  return false;
  }
}
