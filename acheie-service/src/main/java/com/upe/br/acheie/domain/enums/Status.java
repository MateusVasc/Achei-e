package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

  LOST("Lost"),
  FOUND("Found"),
  RETURNED("Returned");

  private final String value;
  
  public static boolean eStatus(String word) {
	  Status[] statuses = Status.values();
	  for (Status status : statuses) {
		  if (status.value.equals(word)) {
			  return true;
		  }
	  }
	  return false;
  }
}
