package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

  LOST("Lost"),
  FOUND("Found"),
  RETURNED("Returned");

  private final String value;
  
  public static boolean eType(String word) {
	  Type[] types = Type.values();
	  for (Type type : types) {
		  if (type.value.equals(word)) {
			  return true;
		  }
	  }
	  return false;
  }
}
