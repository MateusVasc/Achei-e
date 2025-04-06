package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {

  LOST("Lost"),
  FOUND("Found"),
  RETURNED("Returned");

  private final String value;
  
  public static boolean eState(String word) {
	  State[] states = State.values();
	  for (State state : states) {
		  if (state.value.equals(word)) {
			  return true;
		  }
	  }
	  return false;
  }
}
