package com.upe.br.acheie.domain.dto;

public record ErrorDto(String erro, String causa) {

	  public ErrorDto(Exception e) {
	    this(e.getMessage(), e.getCause() != null ? e.getCause().toString() : "");
	  }
}