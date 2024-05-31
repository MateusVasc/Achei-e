package com.upe.br.acheie.dominio.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Categoria {

  ELETRONICO("Eletronico"),
  CHAVES("Chaves");

  private final String valor;
  
  public static boolean eCategoria(String palavra) {
	  Categoria[] categorias = Categoria.values();
	  for (Categoria categoria : categorias) {
		  if (categoria.valor.equals(palavra)) {
			  return true;
		  }
	  }
	  return false;
  }
  
}
