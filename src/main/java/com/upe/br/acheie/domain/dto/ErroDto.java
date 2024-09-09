package com.upe.br.acheie.domain.dto;

public record ErroDto(String erro, String causa) {

	  public ErroDto(Exception e) {
	    this(e.getMessage(), e.getCause() != null ? e.getCause().toString() : "");
	  }
}