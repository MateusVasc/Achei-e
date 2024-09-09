package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Estado {

  PERDIDO("Perdido"),
  ENCONTRADO("Encontrado"),
  DEVOLVIDO("Devolvido");

  private final String valor;
  
  public static boolean eEstado(String palavra) {
	  Estado[] estados = Estado.values();
	  for (Estado estado : estados) {
		  if (estado.valor.equals(palavra)) {
			  return true;
		  }
	  }
	  return false;
  }
}
