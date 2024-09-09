package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tipo {

  PERDIDO("Perdido"),
  ENCONTRADO("Encontrado"),
  DEVOLVIDO("Devolvido");

  private final String valor;
  
  public static boolean eTipo(String palavra) {
	  Tipo[] tipos = Tipo.values();
	  for (Tipo tipo : tipos) {
		  if (tipo.valor.equals(palavra)) {
			  return true;
		  }
	  }
	  return false;
  }
}
